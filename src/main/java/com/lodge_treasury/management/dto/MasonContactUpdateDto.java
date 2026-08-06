package com.lodge_treasury.management.dto;

import com.lodge_treasury.management.enums.ContactPreference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MasonContactUpdateDto {

    @NotBlank(message = "Mobile number is required")
    @Size(max = 20)
    private String mobile;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 255)
    private String address;

    @NotBlank(message = "Emergency contact name is required")
    @Size(max = 100)
    private String emergencyContactName;

    @NotBlank(message = "Emergency contact phone is required")
    @Size(max = 20)
    private String emergencyContactPhone;

    @NotNull(message = "Contact preference is required")
    private ContactPreference contactPreference;

    private String notes;
}
