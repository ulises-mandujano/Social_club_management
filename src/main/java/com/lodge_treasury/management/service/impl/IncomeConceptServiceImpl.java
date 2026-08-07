package com.lodge_treasury.management.service.impl;

import com.lodge_treasury.management.dto.IncomeConceptDto;
import com.lodge_treasury.management.entity.IncomeConcepts;
import com.lodge_treasury.management.exception.ResourceNotFoundException;
import com.lodge_treasury.management.mapper.IncomeConceptMapper;
import com.lodge_treasury.management.repository.IncomeConceptsRepository;
import com.lodge_treasury.management.service.IIncomeConceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeConceptServiceImpl implements IIncomeConceptService {

    private final IncomeConceptsRepository repository;
    private final IncomeConceptMapper mapper;

    @Override
    @Transactional
    public IncomeConceptDto createConcept(IncomeConceptDto dto) {
        IncomeConcepts concepts = mapper.toEntity(dto);
        IncomeConcepts saved = repository.save(concepts);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public IncomeConceptDto updateConcept(Integer id, IncomeConceptDto dto) {
        IncomeConcepts existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income concept not found with id: " + id));
        mapper.updateFromDto(dto, existing);
        IncomeConcepts updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public IncomeConceptDto getConceptById(Integer id) {
        IncomeConcepts concept = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income concept not found with id: " + id));
        return  mapper.toDto(concept);
    }

    @Override
    public List<IncomeConceptDto> getAllConcepts() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteConcept (Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Income concept not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
