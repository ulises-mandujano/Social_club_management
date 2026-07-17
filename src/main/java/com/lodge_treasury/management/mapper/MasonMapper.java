package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MemberDto;
import com.lodge_treasury.management.entity.Mason;

public class MasonMapper {

    public static Mason mapMasonCreateDtoToMason(MasonCreateDto masonCreateDto) {
        Mason mason = new Mason();
        mason.setName(masonCreateDto.getName());
        mason.setSecondName(masonCreateDto.getSecondName());
        mason.setLastName(masonCreateDto.getLastName());
        mason.setSecondLastName(masonCreateDto.getSecondLastName());
        mason.setDateOfBirth(masonCreateDto.getBirthDate());
        return mason;
    }

    public static MemberDto mapMasonToMemberDto(Mason mason, String degree) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(mason.getMasonId());
        memberDto.setName(mason.getName());
        memberDto.setSecondName(mason.getSecondName());
        memberDto.setLastName(mason.getLastName());
        memberDto.setSecondLastName(mason.getSecondLastName());
        memberDto.setBirthDate(mason.getDateOfBirth());
        memberDto.setCurrentDegree(degree);
        return memberDto;
    }
}
