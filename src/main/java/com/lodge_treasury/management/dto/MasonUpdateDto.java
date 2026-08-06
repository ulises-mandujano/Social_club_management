package com.lodge_treasury.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MasonUpdateDto {

    @NotBlank(message = "Name is required")
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String secondName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String secondLastName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private Boolean isFreeMember;
}
