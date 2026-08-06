package com.lodge_treasury.management.mapper;

import com.lodge_treasury.management.dto.MasonContactDto;
import com.lodge_treasury.management.dto.MasonContactUpdateDto;
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

    public static MasonContact mapMasonContactUpdateDtoToMasonContact(MasonContact masonContact,
                                                                      MasonContactUpdateDto updateDto) {
        masonContact.setMobile(updateDto.getMobile());
        masonContact.setEmail(updateDto.getEmail());
        masonContact.setAddress(updateDto.getAddress());
        masonContact.setEmergencyContactName(updateDto.getEmergencyContactName());
        masonContact.setEmergencyContactPhone(updateDto.getEmergencyContactPhone());
        masonContact.setContactPreference(updateDto.getContactPreference());
        masonContact.setNotes(updateDto.getNotes());
        return masonContact;
    }

    public static MasonContactDto mapMasonContactToMasonContactDto (MasonContact contact) {
        if (contact == null) {
            return null;
        }
        MasonContactDto dto = new MasonContactDto();
        dto.setContactId(contact.getContactId());
        dto.setMasonId(contact.getMason().getMasonId());
        dto.setMobile(contact.getMobile());
        dto.setEmail(contact.getEmail());
        dto.setAddress(contact.getAddress());
        dto.setEmergencyContact(contact.getEmergencyContactName());
        dto.setEmergencyContactPhone(contact.getEmergencyContactPhone());
        dto.setContactPreference(contact.getContactPreference());
        dto.setNotes(contact.getNotes());
        return dto;
    }
}
