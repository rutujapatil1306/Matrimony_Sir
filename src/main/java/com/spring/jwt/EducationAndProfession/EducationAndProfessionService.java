package com.spring.jwt.EducationAndProfession;

import java.util.List;

public interface EducationAndProfessionService {

    EducationAndProfessionDTO create(EducationAndProfessionDTO dto);

    EducationAndProfessionDTO getById(Integer id);

    List<EducationAndProfessionDTO> getAll();

    EducationAndProfessionDTO update(Integer id, EducationAndProfessionDTO dto); // partial update (PATCH)

    void delete(Integer id);
}
