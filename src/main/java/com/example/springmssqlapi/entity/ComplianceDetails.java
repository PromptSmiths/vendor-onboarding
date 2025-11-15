package com.example.springmssqlapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "compliance_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Compliance details entity for vendor onboarding")
public class ComplianceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the compliance details", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Tax identification number is mandatory")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{7}|[0-9]{3}-[0-9]{2}-[0-9]{4}|[0-9]{9}$", 
             message = "Tax identification number must be in valid format (EIN: XX-XXXXXXX or SSN: XXX-XX-XXXX or 9 digits)")
    @Column(name = "tax_identification_number", nullable = false)
    @Schema(description = "Tax identification number (EIN or SSN format)", example = "12-3456789", required = true)
    private String taxIdentificationNumber;

    @NotBlank(message = "Business license number is mandatory")
    @Size(max = 50, message = "Business license number cannot exceed 50 characters")
    @Column(name = "business_license_number", nullable = false)
    @Schema(description = "Business license number", example = "BL123456789", required = true)
    private String businessLicenseNumber;

    @NotNull(message = "License expiry date is mandatory")
    @Future(message = "License expiry date must be in the future")
    @Column(name = "license_expiry_date", nullable = false)
    @Schema(description = "Expiry date of the business license (must be future date)", example = "2025-12-31", required = true)
    private LocalDate licenseExpiryDate;

    @NotBlank(message = "Insurance provider is mandatory")
    @Size(max = 100, message = "Insurance provider name cannot exceed 100 characters")
    @Column(name = "insurance_provider", nullable = false)
    @Schema(description = "Name of the insurance provider", example = "State Farm Insurance", required = true)
    private String insuranceProvider;

    @NotBlank(message = "Insurance policy number is mandatory")
    @Size(max = 50, message = "Insurance policy number cannot exceed 50 characters")
    @Column(name = "insurance_policy_number", nullable = false)
    @Schema(description = "Insurance policy number", example = "POL987654321", required = true)
    private String insurancePolicyNumber;

    @NotNull(message = "Insurance expiry date is mandatory")
    @Future(message = "Insurance expiry date must be in the future")
    @Column(name = "insurance_expiry_date", nullable = false)
    @Schema(description = "Expiry date of the insurance policy (must be future date)", example = "2025-06-30", required = true)
    private LocalDate insuranceExpiryDate;

    @Size(max = 500, message = "Industry certifications cannot exceed 500 characters")
    @Column(name = "industry_certifications", length = 500)
    @Schema(description = "Industry certifications (comma-separated)", 
            example = "ISO 9001:2015, SOC 2 Type II, PCI DSS Level 1")
    private String industryCertifications;

    @Lob
    @Column(name = "document_upload")
    @Schema(description = "Compliance documentation (PDF format)", type = "string", format = "binary")
    private byte[] documentUpload;

    @Column(name = "document_filename")
    @Schema(description = "Original filename of the uploaded document", example = "compliance_documents.pdf")
    private String documentFilename;

    @Column(name = "document_content_type")
    @Schema(description = "MIME type of the uploaded document", example = "application/pdf")
    private String documentContentType;
}