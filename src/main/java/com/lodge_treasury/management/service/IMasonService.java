package com.lodge_treasury.management.service;

import com.lodge_treasury.management.dto.*;
import com.lodge_treasury.management.entity.Mason;
import com.lodge_treasury.management.entity.MasonContact;

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

    Mason updateMason (Integer id, MasonUpdateDto updateDto);

    MasonContact updateMasonContact (Integer id, MasonContactUpdateDto updateDto);
}
