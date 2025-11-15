package com.example.springmssqlapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "banking_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Banking details entity for vendor onboarding")
public class BankingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the banking details", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Bank name is mandatory")
    @Size(max = 100, message = "Bank name cannot exceed 100 characters")
    @Column(name = "bank_name", nullable = false)
    @Schema(description = "Name of the bank", example = "JPMorgan Chase Bank", required = true)
    private String bankName;

    @NotBlank(message = "Account holder name is mandatory")
    @Size(max = 255, message = "Account holder name cannot exceed 255 characters")
    @Column(name = "account_holder_name", nullable = false)
    @Schema(description = "Name of the account holder (must match business name)", example = "ABC Corporation Ltd.", required = true)
    private String accountHolderName;

    @NotBlank(message = "Account number is mandatory")
    @Pattern(regexp = "^[0-9]{8,18}$", message = "Account number must be 8-18 digits")
    @Column(name = "account_number", nullable = false)
    @Schema(description = "Bank account number (8-18 digits)", example = "123456789012", required = true)
    private String accountNumber;

    @NotBlank(message = "Account type is mandatory")
    @Pattern(regexp = "^(Checking|Savings|Business)$", 
             message = "Account type must be one of: Checking, Savings, Business")
    @Column(name = "account_type", nullable = false)
    @Schema(description = "Type of bank account", example = "Business", required = true,
            allowableValues = {"Checking", "Savings", "Business"})
    private String accountType;

    @NotBlank(message = "Routing/SWIFT code is mandatory")
    @Pattern(regexp = "^([0-9]{9}|[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?)$", 
             message = "Must be a valid routing number (9 digits) or SWIFT code (8-11 characters)")
    @Column(name = "routing_swift_code", nullable = false)
    @Schema(description = "Bank routing number or SWIFT code", example = "021000021", required = true)
    private String routingSwiftCode;

    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{4}[0-9]{7}([A-Z0-9]?){0,16}$", 
             message = "IBAN must be in valid format")
    @Column(name = "iban")
    @Schema(description = "International Bank Account Number (IBAN)", example = "GB82WEST12345698765432")
    private String iban;

    @NotBlank(message = "Payment terms is mandatory")
    @Pattern(regexp = "^(Net 30|Net 60|Net 90|Due on Receipt|2/10 Net 30|Custom)$", 
             message = "Payment terms must be one of: Net 30, Net 60, Net 90, Due on Receipt, 2/10 Net 30, Custom")
    @Column(name = "payment_terms", nullable = false)
    @Schema(description = "Payment terms for transactions", example = "Net 30", required = true,
            allowableValues = {"Net 30", "Net 60", "Net 90", "Due on Receipt", "2/10 Net 30", "Custom"})
    private String paymentTerms;

    @NotBlank(message = "Currency is mandatory")
    @Pattern(regexp = "^(USD|EUR|GBP|CAD|AUD|JPY|CHF|CNY|INR|Other)$", 
             message = "Currency must be one of: USD, EUR, GBP, CAD, AUD, JPY, CHF, CNY, INR, Other")
    @Column(name = "currency", nullable = false)
    @Schema(description = "Primary currency for transactions", example = "USD", required = true,
            allowableValues = {"USD", "EUR", "GBP", "CAD", "AUD", "JPY", "CHF", "CNY", "INR", "Other"})
    private String currency;

    @Lob
    @Column(name = "document_upload")
    @Schema(description = "Banking verification document (PDF format)", type = "string", format = "binary")
    private byte[] documentUpload;

    @Column(name = "document_filename")
    @Schema(description = "Original filename of the uploaded document", example = "bank_verification.pdf")
    private String documentFilename;

    @Column(name = "document_content_type")
    @Schema(description = "MIME type of the uploaded document", example = "application/pdf")
    private String documentContentType;
}