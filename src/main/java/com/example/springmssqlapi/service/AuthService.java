package com.example.springmssqlapi.service;

import org.springframework.stereotype.Service;

import com.example.springmssqlapi.dto.AuthResponse;
import com.example.springmssqlapi.entity.Vendor;
import com.example.springmssqlapi.repository.VendorRepository;

import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class AuthService {
    
    private final OtpService otpService;
    private final JwtService jwtService;
    private final VendorRepository vendorRepository;
    
    public AuthService(OtpService otpService, JwtService jwtService, VendorRepository vendorRepository) {
		this.otpService = otpService;
		this.jwtService = jwtService;
		this.vendorRepository = vendorRepository;
	}
    
    public void sendOtp(String email) {
        // Get vendor details to retrieve company name
        Vendor vendor = vendorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        
        otpService.sendOtp(email, vendor.getName());
    }
    
    public AuthResponse verifyOtpAndAuthenticate(String email, String otpCode) {
        boolean isOtpValid = otpService.verifyOtp(email, otpCode);
        
        if (!isOtpValid) {
            throw new IllegalArgumentException("Invalid or expired OTP");
        }
        
        // Get vendor details
        Vendor vendor = vendorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        
        if (!vendor.getIsActive()) {
            throw new IllegalArgumentException("Vendor account is not active");
        }
        
        // Generate JWT token
        String token = jwtService.generateToken(email);
        
        log.info("Authentication successful for vendor: {}", email);
        
        return new AuthResponse(token, email, "Authentication successful");
    }
}