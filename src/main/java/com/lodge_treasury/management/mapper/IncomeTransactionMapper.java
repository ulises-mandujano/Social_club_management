package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.IncomeTransactionRequestDto;
import com.lodge_treasury.management.dto.IncomeTransactionResponseDto;
import com.lodge_treasury.management.entity.IncomeTransactions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IncomeTransactionMapper {
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "mason", ignore = true)
    @Mapping(target = "incomeConcept", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    IncomeTransactions toEntity(IncomeTransactionRequestDto dto);

    @Mapping(target = "masonName", source = "mason.name")
    @Mapping(target = "conceptDescription", source = "incomeConcept.description")
    IncomeTransactionResponseDto toResponseDto(IncomeTransactions transaction);
}
