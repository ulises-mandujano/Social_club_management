package com.lodge_treasury.management.dto;

import com.lodge_treasury.management.enums.ContactPreference;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MasonCreateDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeEach
    void setup() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    void shouldPassValidationWhenAllFieldsAreValid() {
        MasonCreateDto masonCreateDto = MasonCreateDto.builder()
                .name("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.of(1980, 1, 1))
                .mobile("5512345678")
                .email("juan@test.com")
                .emergencyContactName("María López")
                .emergencyContactPhone("5587654321")
                .contactPreference(ContactPreference.EMAIL)
                .build();

        Set<ConstraintViolation<MasonCreateDto>> violations = validator.validate(masonCreateDto);
        assertTrue(violations.isEmpty());
     }

    @Test
    void shouldFailWhenNameIsEmpty() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name(null)
                .lastName("Pérez")
                .birthDate(LocalDate.of(1980, 1, 1))
                .mobile("5512345678")
                .email("juan@test.com")
                .emergencyContactName("María López")
                .emergencyContactPhone("5587654321")
                .contactPreference(ContactPreference.EMAIL)
                .build();

        Set<ConstraintViolation<MasonCreateDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Name cannot be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenLastNameIsTooShort() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Juan")
                .lastName("A")
                .birthDate(LocalDate.of(1980, 1, 1))
                .mobile("5512345678")
                .email("juan@test.com")
                .emergencyContactName("María López")
                .emergencyContactPhone("5587654321")
                .contactPreference(ContactPreference.EMAIL)
                .build();

        Set<ConstraintViolation<MasonCreateDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.iterator().next().getMessage().contains("between"));
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.of(1980, 1, 1))
                .mobile("5512345678")
                .email("invalid-email")
                .emergencyContactName("María López")
                .emergencyContactPhone("5587654321")
                .contactPreference(ContactPreference.EMAIL)
                .build();

        Set<ConstraintViolation<MasonCreateDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Email address should be a valid value", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenBirthDateIsInFuture() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.now().plusDays(1))
                .mobile("5512345678")
                .email("juan@test.com")
                .emergencyContactName("María López")
                .emergencyContactPhone("5587654321")
                .contactPreference(ContactPreference.EMAIL)
                .build();

        Set<ConstraintViolation<MasonCreateDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Birth date cannot be in the future", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenMobileNumberHasLetters() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.of(1980, 1, 1))
                .mobile("55123456a8")
                .email("juan@test.com")
                .emergencyContactName("María López")
                .emergencyContactPhone("5587654321")
                .contactPreference(ContactPreference.EMAIL)
                .build();

        Set<ConstraintViolation<MasonCreateDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Mobile number must be 10 digits", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenContactPreferenceIsNull() {
        MasonCreateDto dto = MasonCreateDto.builder()
                .name("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.of(1980, 1, 1))
                .mobile("5512345678")
                .email("juan@test.com")
                .emergencyContactName("María López")
                .emergencyContactPhone("5587654321")
                .contactPreference(null)
                .build();

        Set<ConstraintViolation<MasonCreateDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Contact preference cannot be null", violations.iterator().next().getMessage());
    }
}
