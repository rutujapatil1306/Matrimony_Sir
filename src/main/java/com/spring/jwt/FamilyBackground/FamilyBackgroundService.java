package com.spring.jwt.FamilyBackground;

import java.util.List;

public interface FamilyBackgroundService {

    FamilyBackgroundDTO create(FamilyBackgroundDTO dto);

    FamilyBackgroundDTO getById(Integer id);

    List<FamilyBackgroundDTO> getAll();

    FamilyBackgroundDTO update(Integer id, FamilyBackgroundDTO dto);

    void delete(Integer id);
}
