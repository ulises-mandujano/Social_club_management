package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonCreateDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MasonDegreeMapperTest {

    @Test
    void shouldMapMasonCreateDtoToMasonContact() {
        MasonCreateDto dto = new MasonCreateDto();
        dto.setName("Juan");
        dto.setSecondName("Carlos");
        dto.setLastName("Perez");
        dto.setSecondLastName("Gomez");
        dto.setBirthDate(LocalDate.of(1985,5,15));
        dto.setMobile("5512345678");

    }
}
