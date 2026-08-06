package com.lodge_treasury.management.dto;

import com.lodge_treasury.management.enums.ContactPreference;
import com.lodge_treasury.management.enums.DegreeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Mason Create",
        description = "Schema to hold mason registry information"
)
public class MasonCreateDto {

    @Schema(
            description = "Name of the mason", example = "Juan"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min=2, max=50, message = "The length of the mason name should be between 2 and 50")
    private String name;

    @Schema(
            description = "Second name of the mason (could be null or empty)", example = "Pablo"
    )
    @Size(max=50, message = "The length of the mason second name cannot be greater than 50")
    private String secondName = null;

    @Schema(
            description = "Last name of the mason", example = "Sanchez"
    )
    @NotEmpty(message = "Last name cannot be null or empty")
    @Size(min=2, max=50, message = "The length of the mason last name should be between 5 and 50")
    private String lastName;

    @Schema(
            description = "Second last name of the mason", example = "Hernandez"
    )
    @Size(max=50, message = "The length of the mason second last name cannot be greater than 50")
    private String secondLastName = null;

    @Schema(
            description = "Date of Bith of the mason", example = "1980-12-31"
    )
    @NotNull(message = "Birth date cannot be null")
    @PastOrPresent(message = "Birth date cannot be in the future")
    private LocalDate birthDate;

    @Schema(
            description = "Mobile Number of the mason", example = "5512345678"
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobile;

    @Schema(
            description = "Email of the customer", example = "test@test.com"
    )
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Address of the mason", example = "123 Calle No 12"
    )
    @Size(max=255, message = "The length of the address cannot be greater 255 characters")
    private String address = null;

    @Schema(
            description = "Emergency contact name", example = "Pablo Mendez"
    )
    @NotEmpty(message = "Emergency contact name cannot be null or empty")
    @Size(min=5, max=100, message = "The length of the emergency contact name should be between 5 and 100")
    private String emergencyContactName;

    @Schema(
            description = "Emergency contact number", example = "5512345678"
    )
    @NotEmpty(message = "Emergency contact cannot be null or empty")
    @Pattern(regexp = "[0-9]{10}", message = "Emergency contact must be 10 digits")
    private String emergencyContactPhone;


    @Schema(
            description = "Preferred contact method (EMAIL or PHONE)",
            allowableValues = {"EMAIL", "PHONE"},
            example = "EMAIL"
    )
    @NotNull(message = "Contact preference cannot be null")
    private ContactPreference contactPreference;

    @Schema(
            description = "Additional notes", example = "No contactar después de las 10 p.m."
    )
    private String notes = null;

    @Schema(
            description = "Masonic degree at registration. If not provided, defaults to AM (Entered Apprentice)",
            example = "AM",
            allowableValues = {"AM", "CM", "MM"}
    )
    private DegreeType initialDegree;

    @Schema(
            description = "Received date of the degree", example = "1980-12-31"
    )
    private LocalDate receivedDate;
}
