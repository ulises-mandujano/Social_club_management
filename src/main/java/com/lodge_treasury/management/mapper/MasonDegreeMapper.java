package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.entity.MasonDegree;
import com.lodge_treasury.management.enums.DegreeType;

import java.time.LocalDate;

public class MasonDegreeMapper {

    public static MasonDegree mapMasonCreateDtoToMasonDegree(MasonCreateDto masonCreateDto) {
        DegreeType degree = masonCreateDto.getInitialDegree() != null ? masonCreateDto.getInitialDegree()
                : DegreeType.AM;
        LocalDate receivedDate = masonCreateDto.getReceivedDate() != null ? masonCreateDto.getReceivedDate()
                : LocalDate.now();
        MasonDegree masonDegree = new MasonDegree();
        masonDegree.setDegreeType(degree);
        masonDegree.setReceivedDate(receivedDate);
        return masonDegree;
    }
}
