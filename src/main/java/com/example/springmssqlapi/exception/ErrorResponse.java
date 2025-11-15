package com.example.springmssqlapi.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response returned when API operations fail")
public class ErrorResponse {

    @Schema(description = "Timestamp when the error occurred", example = "2024-11-15T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "HTTP status text", example = "Bad Request")
    private String error;

    @Schema(description = "Error message describing what went wrong", example = "Validation failed")
    private String message;

    @Schema(description = "API path where the error occurred", example = "/vendoronboarding/users")
    private String path;

    @Schema(description = "Detailed error information (for validation errors)", example = "[\"email: Email should be valid\"]")
    private List<String> details;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
