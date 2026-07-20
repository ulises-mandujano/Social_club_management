package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MasonCreateDtoTest;
import com.lodge_treasury.management.dto.MemberDto;
import com.lodge_treasury.management.entity.Mason;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MasonMapperTest {

    @Test
    void shouldMapMasonCreateDtoToMasonWithAllFields() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Juan")
                .secondName("Carlos")
                .lastName("Pérez")
                .secondLastName("Gómez")
                .birthDate(LocalDate.of(1985, 5, 15))
                .build();

        Mason mason = MasonMapper.mapMasonCreateDtoToMason(dto);

        assertAll(
                () -> assertEquals("Juan", mason.getName()),
                () -> assertEquals("Carlos", mason.getSecondName()),
                () -> assertEquals("Pérez", mason.getLastName()),
                () -> assertEquals("Gómez", mason.getSecondLastName()),
                () -> assertEquals(LocalDate.of(1985, 5, 15), mason.getDateOfBirth()),
                () -> assertNull(mason.getMasonId())
        );
    }

    @Test
    void shouldMapMasonCreateDtoToMasonWithOnlyRequiredFields() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Xavier")
                .lastName("Martínez")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        Mason mason = MasonMapper.mapMasonCreateDtoToMason(dto);

        assertAll(
                () -> assertEquals("Xavier", mason.getName()),
                () -> assertNull(mason.getSecondName()),
                () -> assertEquals("Martínez", mason.getLastName()),
                () -> assertNull(mason.getSecondLastName()),
                () -> assertEquals(LocalDate.of(1990, 1, 1), mason.getDateOfBirth())
        );
    }

    @Test
    void shouldMapMasonToMemberDtoWithAllFields() {
        Mason mason = Mason.builder()
                .masonId(100)
                .name("Luis")
                .secondName("Miguel")
                .lastName("López")
                .secondLastName("Martínez")
                .dateOfBirth(LocalDate.of(1990, 10, 20))
                .build();

        String degree = "Maestro Masón";

        MemberDto dto = MasonMapper.mapMasonToMemberDto(mason, degree);

        assertAll(
                () -> assertEquals(100, dto.getId()),
                () -> assertEquals("Luis", dto.getName()),
                () -> assertEquals("Miguel", dto.getSecondName()),
                () -> assertEquals("López", dto.getLastName()),
                () -> assertEquals("Martínez", dto.getSecondLastName()),
                () -> assertEquals(LocalDate.of(1990, 10, 20), dto.getBirthDate()),
                () -> assertEquals("Maestro Masón", dto.getCurrentDegree())
        );
    }

    @Test
    void shouldMapMasonToMemberToMemberDtoWithNullDegree() {
        Mason mason = Mason.builder().masonId(1).name("Pedro").build();
        MemberDto dto = MasonMapper.mapMasonToMemberDto(mason, null);
        assertNull(dto.getCurrentDegree());
    }

    @Test
    void shouldMapMasonToMemberDtoWithEmptyDegree() {
        Mason mason = Mason.builder().masonId(100).name("Pedro").build();
        MemberDto dto = MasonMapper.mapMasonToMemberDto(mason, "");
        assertEquals("", dto.getCurrentDegree());
    }

    @Test
    void shouldHandleMasonWithNullFieldsInMemberDto() {
        Mason mason = Mason.builder().masonId(100).build();
        MemberDto dto = MasonMapper.mapMasonToMemberDto(mason, "Aprendiz");

        assertAll(
                () -> assertEquals(100, dto.getId()),
                () -> assertNull(dto.getName()),
                () -> assertNull(dto.getSecondName()),
                () -> assertNull(dto.getLastName()),
                () -> assertNull(dto.getSecondLastName()),
                () -> assertNull(dto.getBirthDate()),
                () -> assertEquals("Aprendiz", dto.getCurrentDegree())
        );
    }
}
