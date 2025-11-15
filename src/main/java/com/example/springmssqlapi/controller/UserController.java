package com.example.springmssqlapi.controller;

import com.example.springmssqlapi.entity.User;
import com.example.springmssqlapi.service.UserService;
import com.example.springmssqlapi.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing vendor onboarding users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user in the system with validation and sends a welcome email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<User> createUser(
            @Parameter(description = "User data to create", required = true)
            @Valid @RequestBody User user) {

        log.info("POST /users - Creating new user with email: {}", user.getEmail());

        User savedUser = userService.saveUser(user);

        // Send welcome email
        try {
            emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getName());
            log.info("Welcome email sent successfully to: {}", savedUser.getEmail());
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", savedUser.getEmail(), e.getMessage());
            // Don't fail user creation if email fails, just log the error
        }

        log.info("POST /users - Successfully created user with ID: {}", savedUser.getId());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all users in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("GET /users - Fetching all users");

        List<User> users = userService.getAllUsers();

        log.info("GET /users - Returning {} users", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found successfully",
            content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable Long id) {

        log.info("GET /users/{} - Fetching user by ID", id);

        User user = userService.getUser(id);

        log.info("GET /users/{} - Successfully found user with email: {}", id, user.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @Operation(
        summary = "Get user by email",
        description = "Retrieves a specific user by their email address"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found successfully",
            content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email of the user to retrieve", required = true)
            @PathVariable String email) {

        log.info("GET /users/email/{} - Fetching user by email", email);

        User user = userService.getUserByEmail(email);

        log.info("GET /users/email/{} - Successfully found user with ID: {}", email, user.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update user",
        description = "Updates an existing user's information"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated user data", required = true)
            @Valid @RequestBody User userDetails) {

        log.info("PUT /users/{} - Updating user", id);

        User updatedUser = userService.updateUser(id, userDetails);

        log.info("PUT /users/{} - Successfully updated user", id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete user",
        description = "Deletes a user from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long id) {

        log.info("DELETE /users/{} - Deleting user", id);

        userService.deleteUser(id);

        log.info("DELETE /users/{} - Successfully deleted user", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/send-welcome-email")
    @Operation(
        summary = "Send welcome email",
        description = "Sends a welcome email to an existing user"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email sent successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Failed to send email")
    })
    public ResponseEntity<Map<String, String>> sendWelcomeEmail(
            @Parameter(description = "ID of the user to send welcome email to", required = true)
            @PathVariable Long id) {

        log.info("POST /users/{}/send-welcome-email - Sending welcome email", id);

        User user = userService.getUser(id);

        try {
            emailService.sendWelcomeEmail(user.getEmail(), user.getName());
            log.info("Welcome email sent successfully to user: {}", user.getEmail());
            return ResponseEntity.ok(Map.of("message", "Welcome email sent successfully"));
        } catch (Exception e) {
            log.error("Failed to send welcome email to user {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }

    @PostMapping("/send-test-email")
    @Operation(
        summary = "Send test email",
        description = "Sends a test email to verify email configuration"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Test email sent successfully"),
        @ApiResponse(responseCode = "500", description = "Failed to send email")
    })
    public ResponseEntity<Map<String, String>> sendTestEmail(
            @Parameter(description = "Test email request", required = true)
            @RequestBody Map<String, String> request) {

        String toEmail = request.get("email");
        String subject = request.getOrDefault("subject", "Test Email");
        String body = request.getOrDefault("body", "This is a test email from the Vendor Onboarding API.");

        log.info("POST /users/send-test-email - Sending test email to: {}", toEmail);

        try {
            emailService.sendSimpleEmail(toEmail, subject, body);
            log.info("Test email sent successfully to: {}", toEmail);
            return ResponseEntity.ok(Map.of("message", "Test email sent successfully"));
        } catch (Exception e) {
            log.error("Failed to send test email to {}: {}", toEmail, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }
}
