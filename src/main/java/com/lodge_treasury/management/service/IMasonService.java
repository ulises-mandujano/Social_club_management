package com.lodge_treasury.management.service;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.dto.MemberDto;

import java.util.List;

public interface IMasonService {
    /**
     *
     * @param dto - MasonCreateDto Object
     * @return Integer - The Mason created ID
     */
    Integer registerNewMember(MasonCreateDto dto);

    List<MemberDto> findAllMasons();
}
