package com.lodge_treasury.management.controller;

import com.lodge_treasury.management.dto.ApiCustomResponse;
import com.lodge_treasury.management.dto.IncomeConceptDto;
import com.lodge_treasury.management.service.IIncomeConceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Income Concepts", description = "CRUD operations for income concepts")
@RestController
@RequestMapping(path = "/api/v1/income/concepts", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class IncomeConceptController {

    private final IIncomeConceptService conceptService;

    @Operation(summary = "Create a new income concept")
    @ApiResponse(responseCode = "201", description = "Concept created successfully")
    @PostMapping
    public ResponseEntity<ApiCustomResponse<IncomeConceptDto>>
        createConcept (@Valid @RequestBody IncomeConceptDto dto) {
        IncomeConceptDto created = conceptService.createConcept(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiCustomResponse.success("Income concept created successfully", created));
    }

    @Operation(summary = "Get all income concepts")
    @ApiResponse(responseCode = "200", description = "Concepts retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiCustomResponse<List<IncomeConceptDto>>> getAllConcepts () {
        List<IncomeConceptDto> concepts = conceptService.getAllConcepts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiCustomResponse.success("Concepts retrieved successfully", concepts));
    }

    @Operation(summary = "Get an income concept by ID")
    @ApiResponse(responseCode = "200", description = "Concept retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<IncomeConceptDto>> getConceptById (@PathVariable Integer id) {
        IncomeConceptDto concept = conceptService.getConceptById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiCustomResponse.success("Concept retrieved successfully", concept));
    }

    @Operation(summary = "Update an income concept")
    @ApiResponse(responseCode = "200", description = "Concept updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<IncomeConceptDto>> updateConcept (@PathVariable Integer id,
                                                                              @Valid @RequestBody IncomeConceptDto dto) {
        IncomeConceptDto updated = conceptService.updateConcept(id, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiCustomResponse.success("Concept updated successfully", updated));
    }

    @Operation(summary = "Delete an income concept")
    @ApiResponse(responseCode = "204", description = "Concept deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcept (@PathVariable Integer id) {
        conceptService.deleteConcept(id);
        return ResponseEntity.noContent().build();
    }
}
