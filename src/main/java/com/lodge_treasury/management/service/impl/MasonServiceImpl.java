package com.lodge_treasury.management.service.impl;

import com.lodge_treasury.management.dto.*;
import com.lodge_treasury.management.entity.*;
import com.lodge_treasury.management.exception.MasonAlreadyExistsException;
import com.lodge_treasury.management.exception.MasonNotFoundException;
import com.lodge_treasury.management.exception.OutstandingDebtException;
import com.lodge_treasury.management.mapper.AdminMemberMapper;
import com.lodge_treasury.management.mapper.MasonContactMapper;
import com.lodge_treasury.management.mapper.MasonDegreeMapper;
import com.lodge_treasury.management.mapper.MasonMapper;
import com.lodge_treasury.management.repository.*;
import com.lodge_treasury.management.service.IMasonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MasonServiceImpl implements IMasonService {

    private MasonsRepository masonsRepository;
    private MasonContactsRepository contactRepository;
    private MasonDegreesRepository degreesRepository;
    private MasonOfficesRepository masonOfficesRepository;
    private final MasonStatusHistoryRepository statusHistoryRepository;
    private final MasonDegreeMapper masonDegreeMapper;

    /**
     *
     * @param masonCreateDto - MasonCreateDto Object
     * @return - The newly created mason ID
     */
    @Transactional
    @Override
    public Integer registerNewMember(MasonCreateDto masonCreateDto) {
        if(contactRepository.existsByEmail(masonCreateDto.getEmail())) {
            throw new MasonAlreadyExistsException("Ya existe un hermano registrado con el correo electrónico "
                    + masonCreateDto.getEmail());
        }
        if(contactRepository.existsByMobile(masonCreateDto.getMobile())) {
            throw new MasonAlreadyExistsException("Ya existe un hermano registrado con el celular "
                    + masonCreateDto.getMobile());
        }
        Mason mason = MasonMapper.mapMasonCreateDtoToMason(masonCreateDto);
        mason =  masonsRepository.save(mason);
        MasonContact contact = MasonContactMapper.mapMasonCreateDtoToMasonContact(masonCreateDto);
        contact.setMason(mason);
        contactRepository.save(contact);
        MasonDegree masonDegree = masonDegreeMapper.mapMasonCreateDtoToMasonDegree(masonCreateDto);
        masonDegree.setMason(mason);
        Optional<MasonOffices> currentMaster = masonOfficesRepository.findCurrentByOfficeName("Venerable Maestro");
        currentMaster.ifPresent(masonOffices -> masonDegree.setConferredBy(masonOffices.getMason()));
        degreesRepository.save(masonDegree);
        return mason.getMasonId();
    }

    @Override
    public List<MemberDto> findAllMasons(){
        List<Mason> masons = masonsRepository.findAllByDeletedFalse();
        Map<Integer, String> latestDegreeMap = getLatestDegreeMap();

        return masons.stream()
                .map(m -> {
                    String degreeCode = latestDegreeMap.getOrDefault(m.getMasonId(), "AM");
                    return MasonMapper.mapMasonToMemberDto(m, degreeCode);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Mason getMasonById(Integer id) {
        return masonsRepository.findByMasonIdAndDeletedFalse(id)
                .orElseThrow(() -> new MasonNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteMason(Integer id, String reason) {
        Mason mason = getMasonById(id);

        MasonStatusHistory history = new MasonStatusHistory();
        history.setMason(mason);
        history.setStatus(false);
        history.setChangeDate(LocalDate.now());
        history.setReason(reason);
        history.setOutstandingDebt(BigDecimal.ZERO); //TODO Calculate the debt

        statusHistoryRepository.save(history);

        mason.setDeleted(true);
        masonsRepository.save(mason);
    }

    @Override
    @Transactional
    public void restoreMason (Integer id) {
        Mason mason = masonsRepository.findById(id)
                .orElseThrow(() -> new MasonNotFoundException(id));
        if(!mason.isDeleted()) {
            throw new IllegalStateException("Mason is not deleted, cannot restore.");
        }

        Optional<MasonStatusHistory> latestInactiveOpt = statusHistoryRepository.findLatestInactiveByMasonId(id);

        if(latestInactiveOpt.isEmpty()) {
            log.warn("No inactive status history for mason {}. Restoring without modifying history.", id);
        } else {
            MasonStatusHistory inactiveRecord = latestInactiveOpt.get();
            BigDecimal debt = inactiveRecord.getOutstandingDebt();

            if (debt != null && debt.compareTo(BigDecimal.ZERO) > 0) {
                throw new OutstandingDebtException(id, debt);
            }

            statusHistoryRepository.delete(inactiveRecord);
        }
        mason.setDeleted(false);
        masonsRepository.save(mason);
    }

    @Override
    public List<AdminMemberDto> findAllMasonsIncludingDeleted() {
        List<Mason> allMasons = masonsRepository.findAll();
        Map<Integer, String> latestDegreeMap = getLatestDegreeMap();

        return allMasons.stream()
                .map(m -> {
                    String degreeCode = latestDegreeMap.getOrDefault(m.getMasonId(), "AM");
                    MemberDto memberDto = MasonMapper.mapMasonToMemberDto(m, degreeCode);
                    return AdminMemberMapper.fromMemberDtotoAdminMemberDto(memberDto, m.isDeleted());
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void hardDeleteMason (Integer id) {
        Mason mason = masonsRepository.findById(id)
                .orElseThrow(() -> new MasonNotFoundException(id));

        contactRepository.deleteByMasonId(id);
        degreesRepository.deleteByMasonId(id);
        masonsRepository.delete(mason);
    }

    @Override
    @Transactional
    public Mason updateMason (Integer id, MasonUpdateDto updateDto) {
        Mason mason = MasonMapper.mapMasonUpdateDtoToMason(getMasonById(id), updateDto);
        return masonsRepository.save(mason);
    }

    @Override
    @Transactional
    public MasonContact updateMasonContact (Integer id, MasonContactUpdateDto updateDto) {
        Mason mason = getMasonById(id);

        MasonContact contact = mason.getContact();
        if (contact == null) {
            throw new IllegalStateException("Contact information not found for this mason");
        }

        if (contactRepository.existsByEmailAndMason_MasonIdNot(updateDto.getEmail(), id)) {
            throw new MasonAlreadyExistsException("Another member already uses this email: " + updateDto.getEmail());
        }

        if (contactRepository.existsByMobileAndMason_MasonIdNot(updateDto.getMobile(), id)) {
            throw new MasonAlreadyExistsException("Another member already uses this mobile number: " + updateDto.getMobile());
        }

        MasonContactMapper.mapMasonContactUpdateDtoToMasonContact(contact, updateDto);

        return contactRepository.save(contact);
    }

    private Map<Integer, String> getLatestDegreeMap() {
        return degreesRepository.findAllLastestDegrees()
                .stream()
                .collect(Collectors.toMap(
                        md -> md.getMason().getMasonId(),
                        md -> md.getDegree().getDegreeCode()
                ));
    }
}
