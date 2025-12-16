package com.spring.jwt.EducationAndProfession;

import com.spring.jwt.entity.EducationAndProfession;
import com.spring.jwt.entity.User;

public class EducationAndProfessionMapper {

    public static EducationAndProfessionDTO toDTO(EducationAndProfession entity) {
        return new EducationAndProfessionDTO(
                entity.getEducationId(),
                entity.getEducation(),
                entity.getDegree(),
                entity.getOccupation(),
                entity.getOccupationDetails(),
                entity.getIncomePerYear(),
                entity.getEducationAndProfessionalDetailsCol(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

    public static EducationAndProfession toEntity(EducationAndProfessionDTO dto, User user) {
        EducationAndProfession e = new EducationAndProfession();
        e.setEducationId(dto.getEducationId());
        e.setEducation(dto.getEducation());
        e.setDegree(dto.getDegree());
        e.setOccupation(dto.getOccupation());
        e.setOccupationDetails(dto.getOccupationDetails());
        e.setIncomePerYear(dto.getIncomePerYear());
        e.setEducationAndProfessionalDetailsCol(dto.getEducationAndProfessionalDetailsCol());
        e.setUser(user);
        return e;
    }
}
