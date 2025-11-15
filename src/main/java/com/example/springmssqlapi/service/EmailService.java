package com.example.springmssqlapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username:noreply@vendor-onboarding.com}")
    private String fromEmail;
    
    public void sendOtpEmail(String toEmail, String otpCode, String company) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Vendor Login OTP - Vendor Onboarding Portal");
            message.setText(buildOtpEmailContent(otpCode, company));
            
            mailSender.send(message);
            log.info("OTP email sent successfully to: {} for company: {}", toEmail, company);
        } catch (Exception e) {
            log.error("Error sending OTP email to: {} for company: {}", toEmail, company, e);
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
    
    private String buildOtpEmailContent(String otpCode, String company) {
        return String.format(
            "Dear %s,\n\n" +
            "Welcome to the Vendor Onboarding Portal!\n\n" +
            "Your One-Time Password (OTP) for login is: %s\n\n" +
            "This OTP is valid for 10 minutes only. Please do not share this code with anyone.\n\n" +
            "If you did not request this OTP, please ignore this email.\n\n" +
            "Best regards,\n" +
            "Vendor Onboarding Team\n\n" +
            "Note: This is an automated email. Please do not reply to this message.",
            company,
            otpCode
        );
    }
}