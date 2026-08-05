package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.AdminMemberDto;
import com.lodge_treasury.management.dto.MemberDto;

public class AdminMemberMapper {
    public static AdminMemberDto fromMemberDtotoAdminMemberDto (MemberDto memberDto, Boolean deleted) {
        AdminMemberDto adminDto = new AdminMemberDto();

        adminDto.setId(memberDto.getId());
        adminDto.setName(memberDto.getName());
        adminDto.setSecondName(memberDto.getSecondName());
        adminDto.setLastName(memberDto.getLastName());
        adminDto.setSecondLastName(memberDto.getSecondLastName());
        adminDto.setBirthDate(memberDto.getBirthDate());
        adminDto.setCurrentDegree(memberDto.getCurrentDegree());
        adminDto.setDeleted(deleted);
        return adminDto;
    }
}
