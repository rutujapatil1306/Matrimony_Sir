package com.spring.jwt.HoroscopeDetails;

import java.util.List;

public interface HoroscopeDetailsService {

    HoroscopeDetailsDTO create(HoroscopeDetailsDTO dto);

    HoroscopeDetailsDTO getById(Integer id);

    List<HoroscopeDetailsDTO> getAll();

    HoroscopeDetailsDTO update(Integer id, HoroscopeDetailsDTO dto); // PATCH

    void delete(Integer id);
}
