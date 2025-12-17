package com.spring.jwt.PartnerPreference;

import java.util.List;

public interface PartnerPreferenceService {

    PartnerPreferenceDTO create(PartnerPreferenceDTO dto);

    PartnerPreferenceDTO getById(Integer id);

    List<PartnerPreferenceDTO> getAll();

    PartnerPreferenceDTO update(Integer id, PartnerPreferenceDTO dto);

    void delete(Integer id);
}
