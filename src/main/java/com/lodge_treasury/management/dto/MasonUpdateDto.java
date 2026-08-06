package com.lodge_treasury.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema (
        name = "Mason Update",
        description = "Schema to hold mason information update"
)
public class MasonUpdateDto {

    @Schema(
            description = "Name of the mason", example = "Juan"
    )
    @NotBlank(message = "Name is required")
    @Size(max = 50)
    private String name;

    @Schema(
            description = "Second name of the mason (could be null or empty)", example = "Pablo"
    )
    @Size(max = 50)
    private String secondName;

    @Schema(
            description = "Last name of the mason", example = "Sanchez"
    )
    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;

    @Schema(
            description = "Second last name of the mason", example = "Hernandez"
    )
    @Size(max = 50)
    private String secondLastName;

    @Schema(
            description = "Date of Bith of the mason", example = "1980-12-31"
    )
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private Boolean isFreeMember;
}
