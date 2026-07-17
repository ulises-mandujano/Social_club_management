package com.lodge_treasury.management.service.impl;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MemberDto;
import com.lodge_treasury.management.entity.Mason;
import com.lodge_treasury.management.entity.MasonContact;
import com.lodge_treasury.management.entity.MasonDegree;
import com.lodge_treasury.management.entity.MasonOffices;
import com.lodge_treasury.management.exception.MasonAlreadyExistsException;
import com.lodge_treasury.management.mapper.MasonContactMapper;
import com.lodge_treasury.management.mapper.MasonDegreeMapper;
import com.lodge_treasury.management.mapper.MasonMapper;
import com.lodge_treasury.management.repository.MasonContactsRepository;
import com.lodge_treasury.management.repository.MasonDegreesRepository;
import com.lodge_treasury.management.repository.MasonOfficesRepository;
import com.lodge_treasury.management.repository.MasonsRepository;
import com.lodge_treasury.management.service.IMasonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MasonServiceImpl implements IMasonService {

    private MasonsRepository masonsRepository;
    private MasonContactsRepository contactRepository;
    private MasonDegreesRepository degreesRepository;
    private MasonOfficesRepository masonOfficesRepository;

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
        MasonDegree masonDegree = MasonDegreeMapper.mapMasonCreateDtoToMasonDegree(masonCreateDto);
        masonDegree.setMason(mason);
        Optional<MasonOffices> currentMaster = masonOfficesRepository.findCurrentByOfficeName("Venerable Maestro");
        if(currentMaster.isPresent()) {
            masonDegree.setConferredBy(currentMaster.get());
            degreesRepository.save(masonDegree);
        }
        return mason.getMasonId();
    }

    @Override
    public List<MemberDto> findAllMasons(){
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Mason> mason = masonsRepository.findAll();
        mason.forEach(m -> memberDtos.add(MasonMapper.mapMasonToMemberDto(m, "AM")));
        return memberDtos;
    }
}
