package com.spring.jwt.ContactDetails;

import java.util.List;

public interface ContactDetailsService {

    ContactDetailsDTO create(ContactDetailsDTO dto);

    ContactDetailsDTO getById(Integer id);

    List<ContactDetailsDTO> getAll();

    ContactDetailsDTO update(Integer id, ContactDetailsDTO dto);

    void delete(Integer id);
}
