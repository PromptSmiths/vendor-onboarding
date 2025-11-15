package com.example.springmssqlapi.controller;

import com.example.springmssqlapi.dto.ApiResponse;
import com.example.springmssqlapi.dto.AuthResponse;
import com.example.springmssqlapi.dto.SendOtpRequest;
import com.example.springmssqlapi.dto.VerifyOtpRequest;
import com.example.springmssqlapi.service.AuthService;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(@Valid @RequestBody SendOtpRequest request) {
        log.info("OTP request received for email: {} and company: {}", request.getEmail(), request.getCompany());
        
        authService.sendOtp(request.getEmail());
        
        ApiResponse response = new ApiResponse(
            true, 
            "OTP has been sent to your email address. Please check your inbox."
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        log.info("OTP verification request received for email: {}", request.getEmail());
        
        AuthResponse authResponse = authService.verifyOtpAndAuthenticate(
            request.getEmail(), 
            request.getOtpCode()
        );
        
        ApiResponse response = new ApiResponse(true, "OTP verified successfully", authResponse);
        
        return ResponseEntity.ok(response);
    }
}