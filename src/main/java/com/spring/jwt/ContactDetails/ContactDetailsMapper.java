package com.spring.jwt.ContactDetails;

import com.spring.jwt.entity.ContactDetails;
import com.spring.jwt.entity.User;

public class ContactDetailsMapper {

    public static ContactDetailsDTO toDTO(ContactDetails entity) {
        return new ContactDetailsDTO(
                entity.getContactId(),
                entity.getFullAddress(),
                entity.getCity(),
                entity.getPinCode(),
                entity.getMobileNumber(),
                entity.getAlternateNumber(),
                entity.getUser().getId()
        );
    }

    public static ContactDetails toEntity(ContactDetailsDTO dto, User user) {
        ContactDetails entity = new ContactDetails();
        entity.setContactId(dto.getContactId());
        entity.setFullAddress(dto.getFullAddress());
        entity.setCity(dto.getCity());
        entity.setPinCode(dto.getPinCode());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setAlternateNumber(dto.getAlternateNumber());
        entity.setUser(user);
        return entity;
    }
}
