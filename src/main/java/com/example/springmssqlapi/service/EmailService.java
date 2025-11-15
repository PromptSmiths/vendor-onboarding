package com.example.springmssqlapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a simple email to the specified recipient
     * @param toEmail recipient email address
     * @param subject email subject
     * @param body email body content
     */
    public void sendSimpleEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Hackathon 4.0 <no-reply@yourdomain.com>");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Error sending email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Sends a welcome email to a new user
     * @param toEmail recipient email address
     * @param username the username of the new user
     */
    public void sendWelcomeEmail(String toEmail, String username) {
        String subject = "Welcome to Vendor Onboarding Platform";
        String body = String.format(
                "Dear %s,\n\n" +
                "Welcome to the Vendor Onboarding Platform!\n\n" +
                "Your account has been successfully created and you can now access the platform.\n\n" +
                "If you have any questions, please don't hesitate to contact our support team.\n\n" +
                "Best regards,\n" +
                "Vendor Onboarding Team",
                username
        );
        sendSimpleEmail(toEmail, subject, body);
    }

    /**
     * Sends an account activation email
     * @param toEmail recipient email address
     * @param username the username
     * @param activationLink the activation link
     */
    public void sendActivationEmail(String toEmail, String username, String activationLink) {
        String subject = "Activate Your Account";
        String body = String.format(
                "Dear %s,\n\n" +
                "Thank you for registering with our Vendor Onboarding Platform.\n\n" +
                "To activate your account, please click on the following link:\n" +
                "%s\n\n" +
                "If you didn't create this account, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Vendor Onboarding Team",
                username,
                activationLink
        );
        sendSimpleEmail(toEmail, subject, body);
    }

    /**
     * Sends a password reset email
     * @param toEmail recipient email address
     * @param username the username
     * @param resetLink the password reset link
     */
    public void sendPasswordResetEmail(String toEmail, String username, String resetLink) {
        String subject = "Password Reset Request";
        String body = String.format(
                "Dear %s,\n\n" +
                "You have requested to reset your password.\n\n" +
                "To reset your password, please click on the following link:\n" +
                "%s\n\n" +
                "If you didn't request this password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Vendor Onboarding Team",
                username,
                resetLink
        );
        sendSimpleEmail(toEmail, subject, body);
    }
}
