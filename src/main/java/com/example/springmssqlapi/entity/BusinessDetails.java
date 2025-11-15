package com.example.springmssqlapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = "businessRegistrationNumber")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Business details entity for vendor onboarding")
public class BusinessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the business details", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Legal business name is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9\\s&.,'-]+$", message = "Legal business name must contain only alphanumeric characters and common business symbols")
    @Size(max = 255, message = "Legal business name cannot exceed 255 characters")
    @Column(name = "legal_business_name", nullable = false)
    @Schema(description = "Legal name of the business", example = "ABC Corporation Ltd.", required = true)
    private String legalBusinessName;

    @NotBlank(message = "Business registration number is mandatory")
    @Column(name = "business_registration_number", nullable = false, unique = true)
    @Schema(description = "Unique business registration number", example = "REG123456789", required = true)
    private String businessRegistrationNumber;

    @NotBlank(message = "Business type is mandatory")
    @Pattern(regexp = "^(Corporation|LLC|Partnership|Sole Proprietorship|Non-Profit)$", 
             message = "Business type must be one of: Corporation, LLC, Partnership, Sole Proprietorship, Non-Profit")
    @Column(name = "business_type", nullable = false)
    @Schema(description = "Type of business entity", example = "Corporation", required = true, 
            allowableValues = {"Corporation", "LLC", "Partnership", "Sole Proprietorship", "Non-Profit"})
    private String businessType;

    @NotNull(message = "Year established is mandatory")
    @Min(value = 1800, message = "Year established cannot be before 1800")
    @Max(value = 2025, message = "Year established cannot be in the future")
    @Column(name = "year_established", nullable = false)
    @Schema(description = "Year the business was established", example = "2010", required = true, minimum = "1800", maximum = "2025")
    private Integer yearEstablished;

    @NotBlank(message = "Business address is mandatory")
    @Size(max = 1000, message = "Business address cannot exceed 1000 characters")
    @Column(name = "business_address", nullable = false, length = 1000)
    @Schema(description = "Complete business address", example = "123 Main Street\nSuite 100\nNew York, NY 10001\nUSA", required = true)
    private String businessAddress;

    @Min(value = 1, message = "Number of employees must be positive")
    @Column(name = "number_of_employees")
    @Schema(description = "Total number of employees", example = "150", minimum = "1")
    private Integer numberOfEmployees;

    @NotBlank(message = "Industry/Sector is mandatory")
    @Pattern(regexp = "^(Technology|Healthcare|Finance|Manufacturing|Retail|Construction|Education|Government|Other)$", 
             message = "Industry must be one of: Technology, Healthcare, Finance, Manufacturing, Retail, Construction, Education, Government, Other")
    @Column(name = "industry_sector", nullable = false)
    @Schema(description = "Industry or sector of the business", example = "Technology", required = true,
            allowableValues = {"Technology", "Healthcare", "Finance", "Manufacturing", "Retail", "Construction", "Education", "Government", "Other"})
    private String industrySector;

    @Lob
    @Column(name = "document_upload")
    @Schema(description = "Business registration document (PDF format)", type = "string", format = "binary")
    private byte[] documentUpload;

    @Column(name = "document_filename")
    @Schema(description = "Original filename of the uploaded document", example = "business_registration.pdf")
    private String documentFilename;

    @Column(name = "document_content_type")
    @Schema(description = "MIME type of the uploaded document", example = "application/pdf")
    private String documentContentType;
}