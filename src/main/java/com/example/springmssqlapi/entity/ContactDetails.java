package com.example.springmssqlapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "contact_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Contact details entity for vendor onboarding")
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the contact details", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Primary contact name is mandatory")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Primary contact name must contain only alphabets, spaces, hyphens and apostrophes")
    @Size(max = 100, message = "Primary contact name cannot exceed 100 characters")
    @Column(name = "primary_contact_name", nullable = false)
    @Schema(description = "Name of the primary contact person", example = "John Smith", required = true)
    private String primaryContactName;

    @NotBlank(message = "Job title is mandatory")
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    @Column(name = "job_title", nullable = false)
    @Schema(description = "Job title of the primary contact", example = "CEO", required = true)
    private String jobTitle;

    @Email(message = "Email address should be valid")
    @NotBlank(message = "Email address is mandatory")
    @Column(name = "email_address", nullable = false)
    @Schema(description = "Email address of the primary contact", example = "john.smith@company.com", required = true)
    private String emailAddress;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[+]?[0-9\\s()-]{10,20}$", message = "Phone number must be in valid format (10-20 digits with optional country code, spaces, parentheses, and hyphens)")
    @Column(name = "phone_number", nullable = false)
    @Schema(description = "Phone number of the primary contact", example = "+1 (555) 123-4567", required = true)
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = "Secondary contact name must contain only alphabets, spaces, hyphens and apostrophes")
    @Size(max = 100, message = "Secondary contact name cannot exceed 100 characters")
    @Column(name = "secondary_contact_name")
    @Schema(description = "Name of the secondary contact person", example = "Jane Doe")
    private String secondaryContactName;

    @Email(message = "Secondary contact email should be valid")
    @Column(name = "secondary_contact_email")
    @Schema(description = "Email address of the secondary contact", example = "jane.doe@company.com")
    private String secondaryContactEmail;

    @URL(message = "Website must be a valid URL")
    @Column(name = "website")
    @Schema(description = "Company website URL", example = "https://www.company.com")
    private String website;

    @Lob
    @Column(name = "document_upload")
    @Schema(description = "Contact verification document (PDF format)", type = "string", format = "binary")
    private byte[] documentUpload;

    @Column(name = "document_filename")
    @Schema(description = "Original filename of the uploaded document", example = "contact_verification.pdf")
    private String documentFilename;

    @Column(name = "document_content_type")
    @Schema(description = "MIME type of the uploaded document", example = "application/pdf")
    private String documentContentType;
}