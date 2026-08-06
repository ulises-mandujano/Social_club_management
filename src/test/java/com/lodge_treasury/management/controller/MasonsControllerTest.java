package com.lodge_treasury.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lodge_treasury.management.config.TestJpaAuditingConfig;
import com.lodge_treasury.management.dto.*;
import com.lodge_treasury.management.entity.Mason;
import com.lodge_treasury.management.entity.MasonContact;
import com.lodge_treasury.management.enums.ContactPreference;
import com.lodge_treasury.management.enums.DegreeType;
import com.lodge_treasury.management.exception.MasonNotFoundException;
import com.lodge_treasury.management.exception.OutstandingDebtException;
import com.lodge_treasury.management.mapper.MasonMapper;
import com.lodge_treasury.management.service.IMasonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.lodge_treasury.management.mapper.AdminMemberMapper.fromMemberDtotoAdminMemberDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MasonsController.class,
        excludeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = TestJpaAuditingConfig.class
            )
        }
)
public class MasonsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @MockitoBean
    private IMasonService masonService;

    private MasonCreateDto createDto;
    private MasonUpdateDto updateDto;
    private MasonContactUpdateDto contactUpdateDto;
    private Mason mockMason;
    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        createDto = new MasonCreateDto();
        createDto.setName("John");
        createDto.setLastName("Doe");
        createDto.setEmail("john@example.com");
        createDto.setMobile("5551234567");
        createDto.setBirthDate(LocalDate.of(1990, 1, 1));
        createDto.setInitialDegree(DegreeType.CM);
        createDto.setContactPreference(ContactPreference.EMAIL);
        createDto.setEmergencyContactName("Jane Doe");
        createDto.setEmergencyContactPhone("5555678901");

        updateDto = new MasonUpdateDto();
        updateDto.setName("Updated");
        updateDto.setLastName("User");
        updateDto.setDateOfBirth(LocalDate.of(1991, 2, 2));
        updateDto.setIsFreeMember(true);

        contactUpdateDto = new MasonContactUpdateDto();
        contactUpdateDto.setMobile("5559999");
        contactUpdateDto.setEmail("new@example.com");
        contactUpdateDto.setAddress("123 Main St");
        contactUpdateDto.setEmergencyContactName("New Contact");
        contactUpdateDto.setEmergencyContactPhone("5550000");
        contactUpdateDto.setContactPreference(ContactPreference.PHONE);
        contactUpdateDto.setNotes("Updated");

        mockMason = new Mason();
        mockMason.setMasonId(1);
        mockMason.setName("John");
        mockMason.setLastName("Doe");
        mockMason.setDateOfBirth(LocalDate.of(1990, 1, 1));
        mockMason.setDeleted(false);

        memberDto = MasonMapper.mapMasonToMemberDto(mockMason, "CM");
    }

    @Test
    void registerNewMember_shouldReturn201_whenValid() throws Exception {
        when(masonService.registerNewMember(any(MasonCreateDto.class))).thenReturn(1);

        mockMvc.perform(post("/api/v1/registerNewMember")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Mason registered successfully"))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void registerNewMember_shouldReturn400_whenValidationFails() throws Exception {
        createDto.setName(null);

        mockMvc.perform(post("/api/v1/registerNewMember")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void fetchMembers_shouldReturn200_withList() throws Exception {
        when(masonService.findAllMasons()).thenReturn(List.of(memberDto));

        mockMvc.perform(get("/api/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("John"));
    }

    @Test
    void getMason_shouldReturn200_whenExists() throws Exception {
        when(masonService.getMasonById(1)).thenReturn(mockMason);

        mockMvc.perform(get("/api/v1/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John"));
    }

    @Test
    void getMason_shouldReturn404_whenNotFound() throws Exception {
        when(masonService.getMasonById(anyInt())).thenThrow(new MasonNotFoundException(1));

        mockMvc.perform(get("/api/v1/members/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void updateMason_shouldReturn200_whenValid() throws Exception {
        when(masonService.updateMason(anyInt(), any(MasonUpdateDto.class))).thenReturn(mockMason);

        mockMvc.perform(put("/api/v1/members/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Mason updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void updateMason_shouldReturn400_whenInvalid() throws Exception {
        updateDto.setName(null);

        mockMvc.perform(put("/api/v1/members/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateMason_shouldReturn404_whenNotFound() throws Exception {
        when(masonService.updateMason(anyInt(), any(MasonUpdateDto.class))).thenThrow(new MasonNotFoundException(1));

        mockMvc.perform(put("/api/v1/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMason_shouldReturn204_whenValidReason() throws Exception {
        doNothing().when(masonService).deleteMason(anyInt(), any(String.class));

        mockMvc.perform(delete("/api/v1/members/1")
                        .param("reason", "Leaving the lodge"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMason_shouldReturn400_whenReasonMissing() throws Exception {
        mockMvc.perform(delete("/api/v1/members/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteMason_shouldReturn404_whenNotFound() throws Exception {
        doThrow(new MasonNotFoundException(1)).when(masonService).deleteMason(anyInt(), any(String.class));

        mockMvc.perform(delete("/api/v1/members/1")
                        .param("reason", "any"))
                .andExpect(status().isNotFound());
    }

    @Test
    void restoreMason_shouldReturn200_whenSuccess() throws Exception {
        doNothing().when(masonService).restoreMason(anyInt());
        when(masonService.getMasonById(1)).thenReturn(mockMason);

        mockMvc.perform(patch("/api/v1/members/1/restore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Mason restored successfully"));
    }

    @Test
    void restoreMason_shouldReturn400_whenDebtExists() throws Exception {
        doThrow(new OutstandingDebtException(1, java.math.BigDecimal.valueOf(100))).when(masonService).restoreMason(anyInt());

        mockMvc.perform(patch("/api/v1/members/1/restore"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Mason with ID 1 has outstanding debt of 100. Cannot restore."));
    }

    @Test
    void hardDeleteMason_shouldReturn204_whenSuccess() throws Exception {
        doNothing().when(masonService).hardDeleteMason(anyInt());

        mockMvc.perform(delete("/api/v1/members/1/hard"))
                .andExpect(status().isNoContent());
    }

    @Test
    void hardDeleteMason_shouldReturn404_whenNotFound() throws Exception {
        doThrow(new MasonNotFoundException(1)).when(masonService).hardDeleteMason(anyInt());

        mockMvc.perform(delete("/api/v1/members/1/hard"))
                .andExpect(status().isNotFound());
    }

    @Test
    void fetchAllMembersIncludingDeleted_shouldReturn200() throws Exception {
        AdminMemberDto adminDto = fromMemberDtotoAdminMemberDto(memberDto, false);
        when(masonService.findAllMasonsIncludingDeleted()).thenReturn(List.of(adminDto));

        mockMvc.perform(get("/api/v1/members/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].deleted").value(false));
    }

    @Test
    void updateMasonContact_shouldReturn200_whenValid() throws Exception {
        MasonContact mockContact = new MasonContact();
        mockContact.setContactId(1);
        Mason mockMason = new Mason();
        mockMason.setMasonId(1);
        mockContact.setMason(mockMason);
        when(masonService.updateMasonContact(anyInt(), any(MasonContactUpdateDto.class))).thenReturn(mockContact);

        mockMvc.perform(put("/api/v1/members/1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Contact updated successfully"));
    }

    @Test
    void updateMasonContact_shouldReturn400_whenInvalid() throws Exception {
        contactUpdateDto.setEmail("invalid");

        mockMvc.perform(put("/api/v1/members/1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateMasonContact_shouldReturn404_whenMasonNotFound() throws Exception {
        when(masonService.updateMasonContact(anyInt(), any(MasonContactUpdateDto.class)))
                .thenThrow(new MasonNotFoundException(1));

        mockMvc.perform(put("/api/v1/members/1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactUpdateDto)))
                .andExpect(status().isNotFound());
    }
}
