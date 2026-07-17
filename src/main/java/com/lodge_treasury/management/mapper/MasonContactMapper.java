package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonCreateDto;
import com.lodge_treasury.management.entity.MasonContact;

public class MasonContactMapper {

    public static MasonContact mapMasonCreateDtoToMasonContact(MasonCreateDto masonCreateDto) {
        MasonContact masonContact = new MasonContact();
        masonContact.setMobile(masonCreateDto.getMobile());
        masonContact.setEmail(masonCreateDto.getEmail());
        masonContact.setAddress(masonCreateDto.getAddress());
        masonContact.setEmergencyContactName(masonCreateDto.getEmergencyContactName());
        masonContact.setEmergencyContactPhone(masonCreateDto.getEmergencyContactPhone());
        masonContact.setContactPreference(masonCreateDto.getContactPreference());
        masonContact.setNotes(masonCreateDto.getNotes());
        return masonContact;
    }
}
