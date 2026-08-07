package com.lodge_treasury.management.controller;

import com.lodge_treasury.management.dto.ApiCustomResponse;
import com.lodge_treasury.management.dto.IncomeTransactionRequestDto;
import com.lodge_treasury.management.dto.IncomeTransactionResponseDto;
import com.lodge_treasury.management.service.IIncomeTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@Tag(name = "Income Transactions", description = "Operations for recording and viewing income transactions")
@RestController
@RequestMapping(path = "/api/v1/income/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class IncomeTransactionController {

    private final IIncomeTransactionService transactionService;

    @Operation(summary = "Record a new income transaction")
    @ApiResponse(responseCode = "201", description = "Transaction recorded successfully")
    @PostMapping
    public ResponseEntity<ApiCustomResponse<IncomeTransactionResponseDto>> recordTransaction
            (@Valid @RequestBody IncomeTransactionRequestDto dto) {
        IncomeTransactionResponseDto recorded = transactionService.recordTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiCustomResponse.success("Income transaction recorded successfully", recorded));
    }

    @Operation(summary = "Get all income transactions")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    @GetMapping
    public ResponseEntity<ApiCustomResponse<List<IncomeTransactionResponseDto>>> getAllTransactions () {
        List<IncomeTransactionResponseDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(ApiCustomResponse.success("Transactions retrieved successfully", transactions));
    }

    @Operation(summary = "Get income transactions by mason ID")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    @GetMapping("/member/{masonId}")
    public ResponseEntity<ApiCustomResponse<List<IncomeTransactionResponseDto>>> getTransactionsByMason(
            @PathVariable Integer masonId) {
        List<IncomeTransactionResponseDto> transactions = transactionService.getTransactionsByMason(masonId);
        return ResponseEntity.ok(ApiCustomResponse.success("Transactions retrieved successfully", transactions));
    }

    @Operation(summary = "Get a specific income transaction by ID")
    @ApiResponse(responseCode = "200", description = "Transaction retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ApiCustomResponse<IncomeTransactionResponseDto>> getTransactionById(@PathVariable Integer id) {
        IncomeTransactionResponseDto transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(ApiCustomResponse.success("Transaction retrieved successfully", transaction));
    }
}
