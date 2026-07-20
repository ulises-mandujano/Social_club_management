package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MasonCreateDtoTest;
import com.lodge_treasury.management.entity.Degree;
import com.lodge_treasury.management.entity.MasonDegree;
import com.lodge_treasury.management.enums.ContactPreference;
import com.lodge_treasury.management.enums.DegreeType;
import com.lodge_treasury.management.exception.DegreeNotFoundException;
import com.lodge_treasury.management.repository.DegreeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MasonDegreeMapperTest {

    @Mock
    private DegreeRepository degreeRepository;

    @InjectMocks
    private MasonDegreeMapper mapper;

    @Test
    void shouldMapDtoToMasonDegreeWithDefaultDegreeAndDate(){
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.of(1980, 1, 1))
                .mobile("5512345678")
                .email("juan@test.com")
                .emergencyContactName("María")
                .emergencyContactPhone("5512345679")
                .contactPreference(ContactPreference.EMAIL)
                .build();

        Degree degreeAM = new Degree();
        degreeAM.setDegreeCode("AM");
        degreeAM.setDegreeName("Aprendiz");
        when(degreeRepository.findByDegreeCode("AM")).thenReturn(Optional.of(degreeAM));

        MasonDegree result = mapper.mapMasonCreateDtoToMasonDegree(dto);

        assertNotNull(result);
        assertNotNull(result.getDegree());
        assertEquals("AM", result.getDegree().getDegreeCode());
        assertNotNull(result.getReceivedDate());
        assertEquals(LocalDate.now(), result.getReceivedDate());
    }

    @Test
    void shouldMapDtoToMasonDegreeWithCustomValues() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Carlos")
                .lastName("García")
                .birthDate(LocalDate.of(1975, 5, 10))
                .mobile("5512345670")
                .email("carlos@test.com")
                .emergencyContactName("Laura")
                .emergencyContactPhone("5512345671")
                .contactPreference(ContactPreference.PHONE)
                .initialDegree(DegreeType.MM)
                .receivedDate(LocalDate.of(2020, 1, 1))
                .build();

        Degree degreeMM = new Degree();
        degreeMM.setDegreeCode("MM");
        degreeMM.setDegreeName("Maestro");
        when(degreeRepository.findByDegreeCode("MM")).thenReturn(Optional.of(degreeMM));

        MasonDegree result = mapper.mapMasonCreateDtoToMasonDegree(dto);

        assertEquals("MM", result.getDegree().getDegreeCode());
        assertEquals(LocalDate.of(2020, 1, 1), result.getReceivedDate());
    }

    @Test
    void shouldThrowExceptionWhenDegreeNotFound() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Ana")
                .lastName("López")
                .birthDate(LocalDate.of(1990, 3, 15))
                .mobile("5512345672")
                .email("ana@test.com")
                .emergencyContactName("Luis")
                .emergencyContactPhone("5512345673")
                .contactPreference(ContactPreference.EMAIL)
                .initialDegree(DegreeType.CM)
                .build();

        when(degreeRepository.findByDegreeCode("CM")).thenReturn(Optional.empty());

        DegreeNotFoundException exception = assertThrows(DegreeNotFoundException.class,
                () -> mapper.mapMasonCreateDtoToMasonDegree(dto));

        assertEquals("Grado no encontrado con código: CM", exception.getMessage());
    }
}
