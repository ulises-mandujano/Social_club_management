package com.lodge_treasury.management.dto;

import com.lodge_treasury.management.enums.ContactPreference;
import lombok.Data;

@Data
public class MasonContactDto {
    private Integer masonId;
    private String mobile;
    private String email;
    private String address;
    private String emergencyContact;
    private String emergencyContactPhone;
    private ContactPreference contactPreference;
    private String notes;
}
