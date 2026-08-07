package com.lodge_treasury.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeConceptDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer incomeId;

    @NotBlank(message = "Description is required")
    @Size(max = 100)
    private String description;
}
