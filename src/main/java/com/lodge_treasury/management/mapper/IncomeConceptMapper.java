package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.IncomeConceptDto;
import com.lodge_treasury.management.entity.IncomeConcepts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IncomeConceptMapper {

    IncomeConceptDto toDto(IncomeConcepts concept);

    @Mapping(target = "incomeId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    IncomeConcepts toEntity(IncomeConceptDto dto);

    @Mapping(target = "incomeId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateFromDto(IncomeConceptDto dto, @MappingTarget IncomeConcepts concept);
}
