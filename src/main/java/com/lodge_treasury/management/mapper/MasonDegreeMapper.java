package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.entity.Degree;
import com.lodge_treasury.management.entity.MasonDegree;
import com.lodge_treasury.management.enums.DegreeType;
import com.lodge_treasury.management.exception.DegreeNotFoundException;
import com.lodge_treasury.management.repository.DegreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MasonDegreeMapper {

    private final DegreeRepository degreeRepository;

    public MasonDegree mapMasonCreateDtoToMasonDegree(MasonCreateDto dto) {
        DegreeType degreeType = dto.getInitialDegree() != null
                ? dto.getInitialDegree()
                : DegreeType.AM;

        LocalDate receivedDate = dto.getReceivedDate() != null
                ? dto.getReceivedDate()
                : LocalDate.now();

        Degree degree = degreeRepository.findByDegreeCode(degreeType.name())
                .orElseThrow(() -> new DegreeNotFoundException(degreeType.name()));

        MasonDegree masonDegree = new MasonDegree();
        masonDegree.setDegree(degree);
        masonDegree.setReceivedDate(receivedDate);

        return masonDegree;
    }
}
