package com.example.springmssqlapi.controller;

import com.example.springmssqlapi.entity.User;
import com.example.springmssqlapi.service.UserService;
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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing vendor onboarding users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user in the system with validation"
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
}
