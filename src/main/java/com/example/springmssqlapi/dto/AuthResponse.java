package com.example.springmssqlapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private String email;
    private String message;
    
    public AuthResponse(String token, String email, String message) {
        this.token = token;
        this.email = email;
        this.message = message;
        this.tokenType = "Bearer";
    }

    public AuthResponse(String token, String tokenType, String email, String message) {
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
        this.message = message;
    }
}