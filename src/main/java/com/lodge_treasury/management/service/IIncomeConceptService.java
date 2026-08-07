package com.lodge_treasury.management.service;

import com.lodge_treasury.management.dto.IncomeConceptDto;

import java.util.List;

public interface IIncomeConceptService {

    IncomeConceptDto createConcept(IncomeConceptDto dto);

    IncomeConceptDto updateConcept(Integer id, IncomeConceptDto dto);

    IncomeConceptDto getConceptById(Integer id);

    List<IncomeConceptDto> getAllConcepts();

    void deleteConcept(Integer id);
}
