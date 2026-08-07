package com.lodge_treasury.management.service.impl;

import com.lodge_treasury.management.dto.IncomeTransactionRequestDto;
import com.lodge_treasury.management.dto.IncomeTransactionResponseDto;
import com.lodge_treasury.management.entity.IncomeConcepts;
import com.lodge_treasury.management.entity.IncomeTransactions;
import com.lodge_treasury.management.entity.Mason;
import com.lodge_treasury.management.exception.ResourceNotFoundException;
import com.lodge_treasury.management.mapper.IncomeTransactionMapper;
import com.lodge_treasury.management.repository.IncomeConceptsRepository;
import com.lodge_treasury.management.repository.IncomeTransactionsRepository;
import com.lodge_treasury.management.repository.MasonsRepository;
import com.lodge_treasury.management.service.IIncomeTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeTransactionServiceImpl implements IIncomeTransactionService {

    private final IncomeTransactionsRepository transactionsRepository;
    private final MasonsRepository masonsRepository;
    private final IncomeConceptsRepository conceptsRepository;
    private final IncomeTransactionMapper mapper;

    @Override
    @Transactional
    public IncomeTransactionResponseDto recordTransaction (IncomeTransactionRequestDto dto) {
        Mason mason = masonsRepository.findById(dto.getMasonId())
                .orElseThrow(() -> new ResourceNotFoundException("Mason not found with id: " + dto.getMasonId()));

        IncomeConcepts concept = conceptsRepository.findById(dto.getConceptId())
                .orElseThrow(() -> new ResourceNotFoundException("Income concept not found with id: "
                        + dto.getConceptId()));

        IncomeTransactions transaction = mapper.toEntity(dto);
        transaction.setMason(mason);
        transaction.setIncomeConcept(concept);

        IncomeTransactions saved = transactionsRepository.save(transaction);
        return mapper.toResponseDto(saved);
    }

    @Override
    public List<IncomeTransactionResponseDto> getTransactionsByMason (Integer masonId) {
        if (!masonsRepository.existsById(masonId)) {
            throw new ResourceNotFoundException("Mason not found with id: " + masonId);
        }
        List<IncomeTransactions> transactions = transactionsRepository.findByMason_MasonId(masonId);
        return transactions.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncomeTransactionResponseDto> getAllTransactions () {
        return transactionsRepository.findAll().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public IncomeTransactionResponseDto getTransactionById (Integer id) {
        IncomeTransactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return mapper.toResponseDto(transaction);
    }
}
