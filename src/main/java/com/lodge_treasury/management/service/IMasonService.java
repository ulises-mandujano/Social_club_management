package com.lodge_treasury.management.service;

import com.lodge_treasury.management.dto.AdminMemberDto;
import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MemberDto;
import com.lodge_treasury.management.entity.Mason;

import java.util.List;

public interface IMasonService {
    /**
     *
     * @param dto - MasonCreateDto Object
     * @return Integer - The Mason created ID
     */
    Integer registerNewMember(MasonCreateDto dto);

    List<MemberDto> findAllMasons();

    Mason getMasonById(Integer id);

    void deleteMason (Integer id, String reason);

    void restoreMason (Integer id);

    List<AdminMemberDto> findAllMasonsIncludingDeleted();

    void hardDeleteMason(Integer id);
}
