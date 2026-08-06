package com.lodge_treasury.management.service.impl;

import com.lodge_treasury.management.dto.MasonContactUpdateDto;
import com.lodge_treasury.management.dto.MasonUpdateDto;
import com.lodge_treasury.management.entity.Mason;
import com.lodge_treasury.management.entity.MasonContact;
import com.lodge_treasury.management.entity.MasonStatusHistory;
import com.lodge_treasury.management.enums.ContactPreference;
import com.lodge_treasury.management.exception.MasonAlreadyExistsException;
import com.lodge_treasury.management.exception.MasonNotFoundException;
import com.lodge_treasury.management.exception.OutstandingDebtException;
import com.lodge_treasury.management.mapper.MasonDegreeMapper;
import com.lodge_treasury.management.repository.MasonContactsRepository;
import com.lodge_treasury.management.repository.MasonOfficesRepository;
import com.lodge_treasury.management.repository.MasonStatusHistoryRepository;
import com.lodge_treasury.management.repository.MasonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MasonServiceImplTest {

    @Mock
    private MasonsRepository masonsRepository;

    @Mock
    private MasonContactsRepository contactsRepository;

    @Mock
    private MasonOfficesRepository masonOfficesRepository;

    @Mock
    private MasonStatusHistoryRepository statusHistoryRepository;

    @Mock
    private MasonDegreeMapper masonDegreeMapper;

    @InjectMocks
    private MasonServiceImpl masonService;

    private Mason mason;
    private MasonStatusHistory inactiveHistory;

    @BeforeEach
    void setUp() {
        mason = new Mason();
        mason.setMasonId(1);
        mason.setDeleted(true);

        inactiveHistory = new MasonStatusHistory();
        inactiveHistory.setHistoryId(100);
        inactiveHistory.setMason(mason);
        inactiveHistory.setStatus(false);
        inactiveHistory.setOutstandingDebt(BigDecimal.ZERO);
        inactiveHistory.setChangeDate(LocalDate.now());
    }

    @Test
    void restoreMason_shouldThrow_whenMasonNotFound() {
        when(masonsRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> masonService.restoreMason(1))
                .isInstanceOf(MasonNotFoundException.class)
                .hasMessageContaining("1");

        verify(masonsRepository).findById(1);
        verifyNoMoreInteractions(masonsRepository);
        verifyNoInteractions(statusHistoryRepository);
    }

    @Test
    void restoreMason_shouldThrow_whenMasonIsNotDeleted() {
        mason.setDeleted(false);
        when(masonsRepository.findById(1)).thenReturn(Optional.of(mason));

        assertThatThrownBy(() -> masonService.restoreMason(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Mason is not deleted, cannot restore.");

        verify(masonsRepository).findById(1);
        verifyNoInteractions(statusHistoryRepository);
    }

    @Test
    void restoreMason_shouldThrow_whenDebtGreaterThanZero() {
        inactiveHistory.setOutstandingDebt(BigDecimal.valueOf(150.00));
        when(masonsRepository.findById(1)).thenReturn(Optional.of(mason));
        when(statusHistoryRepository.findLatestInactiveByMasonId(1))
                .thenReturn(Optional.of(inactiveHistory));

        assertThatThrownBy(() -> masonService.restoreMason(1))
                .isInstanceOf(OutstandingDebtException.class)
                .hasMessageContaining("outstanding debt of 150");

        verify(statusHistoryRepository).findLatestInactiveByMasonId(1);
        verify(statusHistoryRepository, never()).delete(any());
        verify(masonsRepository, never()).save(any());
    }

    @Test
    void restoreMason_shouldRestoreAndDeleteHistory_whenDebtIsZero() {
        when(masonsRepository.findById(1)).thenReturn(Optional.of(mason));
        when(statusHistoryRepository.findLatestInactiveByMasonId(1)).thenReturn(Optional.of(inactiveHistory));

        masonService.restoreMason(1);

        verify(statusHistoryRepository).delete(inactiveHistory);
        verify(masonsRepository).save(mason);
        assertThat(mason.isDeleted()).isFalse();
    }

    @Test
    void restoreMason_shouldRestoreWithoutDeletingHistory_whenNoInactiveHistoryFound() {
        when(masonsRepository.findById(1)).thenReturn(Optional.of(mason));
        when(statusHistoryRepository.findLatestInactiveByMasonId(1)).thenReturn(Optional.empty());

        masonService.restoreMason(1);

        verify(statusHistoryRepository, never()).delete(any());
        verify(masonsRepository).save(mason);
        assertThat(mason.isDeleted()).isFalse();
    }

    @Test
    void deleteMason_shouldCreateHistoryAndSetDeleted_whenMasonExistsAndActive() {
        mason.setDeleted(false);
        when(masonsRepository.findByMasonIdAndDeletedFalse(1))
                .thenReturn(Optional.of(mason));
        when(statusHistoryRepository.save(any(MasonStatusHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(masonsRepository.save(any(Mason.class)))
                .thenReturn(mason);

        String reason = "Left the lodge";

        masonService.deleteMason(1, reason);

        verify(statusHistoryRepository).save(any(MasonStatusHistory.class));
        verify(masonsRepository).save(mason);
        assertThat(mason.isDeleted()).isTrue();
    }

    @Test
    void deleteMason_shouldThrow_whenMasonNotFound() {
        when(masonsRepository.findByMasonIdAndDeletedFalse(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> masonService.deleteMason(1, "any"))
                .isInstanceOf(MasonNotFoundException.class);
    }

    @Test
    void updateMason_shouldUpdateFieldsAndSave() {
        MasonUpdateDto updateDto = new MasonUpdateDto();
        updateDto.setName("UpdatedName");
        updateDto.setLastName("UpdatedLastName");
        updateDto.setDateOfBirth(LocalDate.of(1990,1,1));
        updateDto.setIsFreeMember(true);

        when(masonsRepository.findByMasonIdAndDeletedFalse(1))
                .thenReturn(Optional.of(mason));
        when(masonsRepository.save(any(Mason.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mason updated = masonService.updateMason(1, updateDto);

        assertThat(updated.getName()).isEqualTo("UpdatedName");
        assertThat(updated.getLastName()).isEqualTo("UpdatedLastName");
        assertThat(updated.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(updated.getIsFreeMember()).isTrue();

        verify(masonsRepository).save(mason);
    }

    @Test
    void updateMasonContact_shouldUpdateAndSave_whenValid() {
        MasonContact contact = new MasonContact();
        contact.setContactId(10);
        contact.setMason(mason);
        mason.setContact(contact);

        MasonContactUpdateDto updateDto = new MasonContactUpdateDto();
        updateDto.setMobile("5551234567");
        updateDto.setEmail("new@example.com");
        updateDto.setAddress("123 Main St");
        updateDto.setEmergencyContactName("Jane Doe");
        updateDto.setEmergencyContactPhone("5512345678");
        updateDto.setContactPreference(ContactPreference.EMAIL);
        updateDto.setNotes("Updated notes");

        when(masonsRepository.findByMasonIdAndDeletedFalse(1))
                .thenReturn(Optional.of(mason));
        when(contactsRepository.existsByEmailAndMason_MasonIdNot(updateDto.getEmail(), 1))
                .thenReturn(false);
        when(contactsRepository.existsByMobileAndMason_MasonIdNot(updateDto.getMobile(), 1))
                .thenReturn(false);
        when(contactsRepository.save(any(MasonContact.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MasonContact updated = masonService.updateMasonContact(1, updateDto);

        assertThat(updated.getMobile()).isEqualTo("5551234567");
        assertThat(updated.getEmail()).isEqualTo("new@example.com");
        assertThat(updated.getAddress()).isEqualTo("123 Main St");
        assertThat(updated.getEmergencyContactName()).isEqualTo("Jane Doe");
        assertThat(updated.getEmergencyContactPhone()).isEqualTo("5512345678");
        assertThat(updated.getContactPreference()).isEqualTo(com.lodge_treasury.management.enums.ContactPreference.EMAIL);
        assertThat(updated.getNotes()).isEqualTo("Updated notes");

        verify(contactsRepository).save(contact);
    }

    @Test
    void updateMasonContact_shouldThrow_whenEmailAlreadyUsedByAnother() {
        MasonContact contact = new MasonContact();
        contact.setContactId(10);
        contact.setMason(mason);
        mason.setContact(contact);

        MasonContactUpdateDto updateDto = new MasonContactUpdateDto();
        updateDto.setEmail("existing@example.com");
        updateDto.setMobile("5550000");
        updateDto.setEmergencyContactName("Name");
        updateDto.setEmergencyContactPhone("Phone");
        updateDto.setContactPreference(com.lodge_treasury.management.enums.ContactPreference.EMAIL);

        when(masonsRepository.findByMasonIdAndDeletedFalse(1))
                .thenReturn(Optional.of(mason));
        when(contactsRepository.existsByEmailAndMason_MasonIdNot(updateDto.getEmail(), 1))
                .thenReturn(true);

        assertThatThrownBy(() -> masonService.updateMasonContact(1, updateDto))
                .isInstanceOf(MasonAlreadyExistsException.class)
                .hasMessageContaining("Another member already uses this email");

        verify(contactsRepository, never()).save(any());
    }

    @Test
    void getMasonById_shouldReturnMason_whenActiveAndExists() {
        when(masonsRepository.findByMasonIdAndDeletedFalse(1))
                .thenReturn(Optional.of(mason));

        Mason found = masonService.getMasonById(1);

        assertThat(found).isEqualTo(mason);
    }

    @Test
    void getMasonById_shouldThrow_whenNotFound() {
        when(masonsRepository.findByMasonIdAndDeletedFalse(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> masonService.getMasonById(1))
                .isInstanceOf(MasonNotFoundException.class);
    }
}
