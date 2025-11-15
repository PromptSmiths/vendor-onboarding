package com.example.springmssqlapi.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.springmssqlapi.entity.OtpCode;
import com.example.springmssqlapi.entity.Vendor;
import com.example.springmssqlapi.repository.OtpCodeRepository;
import com.example.springmssqlapi.repository.VendorRepository;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    
    private final OtpCodeRepository otpCodeRepository;
    private final VendorRepository vendorRepository;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();
    
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 10;
    private static final int MAX_RETRY_COUNT = 5;
    
    @Transactional
    public void sendOtp(String email, String company) {
        // Check if vendor exists, if not create a new one
        if (!vendorRepository.existsByEmail(email)) {
            Vendor newVendor = new Vendor();
            newVendor.setEmail(email);
            newVendor.setName(company);
            newVendor.setIsActive(true);
            vendorRepository.save(newVendor);
            log.info("New vendor created with email: {} and company: {}", email, company);
        }
        
        // Mark any existing OTPs as used for this email
        otpCodeRepository.markAllOtpsAsUsedForEmail(email);
        
        // Generate new OTP
        String otpCode = generateOtp();
        
        // Save OTP to database
        OtpCode otp = new OtpCode();
        otp.setEmail(email);
        otp.setCompany(company);
        otp.setOtpCode(otpCode);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otp.setIsUsed(false);
        otp.setRetryCount(0);
        
        otpCodeRepository.save(otp);
        
        // Send email
        emailService.sendOtpEmail(email, company, otpCode);

        log.info("OTP generated and sent for email: {} and company: {}", email, company);
    }
    
    @Transactional
    public boolean verifyOtp(String email, String otpCode) {
        Optional<OtpCode> otpOptional = otpCodeRepository.findByEmailAndOtpCodeAndIsUsedFalse(email, otpCode);
        
        if (otpOptional.isEmpty()) {
            log.warn("Invalid OTP attempt for email: {}", email);
            incrementRetryCount(email);
            return false;
        }
        
        OtpCode otp = otpOptional.get();
        
        if (!otp.isValidForVerification()) {
            log.warn("OTP verification failed - expired or max retries reached for email: {}", email);
            return false;
        }
        
        // Mark OTP as used
        otp.setIsUsed(true);
        otpCodeRepository.save(otp);
        
        // Mark all other OTPs for this email as used
        otpCodeRepository.markAllOtpsAsUsedForEmail(email);
        
        log.info("OTP verification successful for email: {}", email);
        return true;
    }
    
    private void incrementRetryCount(String email) {
        Optional<OtpCode> latestOtpOptional = otpCodeRepository.findTopByEmailAndIsUsedFalseOrderByCreatedAtDesc(email);
        if (latestOtpOptional.isPresent()) {
            OtpCode latestOtp = latestOtpOptional.get();
            latestOtp.setRetryCount(latestOtp.getRetryCount() + 1);
            
            if (latestOtp.getRetryCount() >= MAX_RETRY_COUNT) {
                latestOtp.setIsUsed(true);
                log.warn("OTP marked as used due to max retry attempts for email: {}", email);
            }
            
            otpCodeRepository.save(latestOtp);
        }
    }
    
    private String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(secureRandom.nextInt(10));
        }
        return otp.toString();
    }
    
    @Scheduled(fixedRate = 3600000) // Run every hour
    @Transactional
    public void cleanupExpiredOtps() {
        otpCodeRepository.deleteExpiredOtps(LocalDateTime.now());
        log.info("Cleanup completed for expired OTPs");
    }
}