package com.lodge_treasury.management.service;

import com.lodge_treasury.management.dto.IncomeTransactionRequestDto;
import com.lodge_treasury.management.dto.IncomeTransactionResponseDto;

import java.util.List;

public interface IIncomeTransactionService {

    IncomeTransactionResponseDto recordTransaction(IncomeTransactionRequestDto dto);

    List<IncomeTransactionResponseDto> getTransactionsByMason(Integer masonId);

    List<IncomeTransactionResponseDto> getAllTransactions();

    IncomeTransactionResponseDto getTransactionById(Integer id);
}
