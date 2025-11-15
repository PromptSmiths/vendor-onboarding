package com.example.springmssqlapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User entity for vendor onboarding system")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", nullable = false)
    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Email address of the user (must be unique)", example = "john.doe@example.com", required = true)
    private String email;

    @Column(name = "email_verified", nullable = false)
    @Schema(description = "Whether the user's email has been verified", example = "false", defaultValue = "false")
    private Boolean emailVerified = false;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.emailVerified = false;
    }
}
