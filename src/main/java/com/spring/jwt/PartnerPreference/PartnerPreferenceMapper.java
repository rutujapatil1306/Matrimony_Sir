package com.spring.jwt.PartnerPreference;

import com.spring.jwt.entity.PartnerPreference;
import com.spring.jwt.entity.User;

public class PartnerPreferenceMapper {

    public static PartnerPreferenceDTO toDTO(PartnerPreference entity) {
        return new PartnerPreferenceDTO(
                entity.getPartnerPreferenceId(),
                entity.getAge(),
                entity.getLookingFor(),
                entity.getHeight(),
                entity.getEatingHabits(),
                entity.getCountryLivingIn(),
                entity.getCityLivingIn(),
                entity.getComplexion(),
                entity.getReligion(),
                entity.getCaste(),
                entity.getEducation(),
                entity.getMangal(),
                entity.getResidentStatus(),
                entity.getPartnerOccupation(),
                entity.getPartnerIncome(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

    public static PartnerPreference toEntity(PartnerPreferenceDTO dto, User user) {
        PartnerPreference entity = new PartnerPreference();

        entity.setPartnerPreferenceId(dto.getPartnerPreferenceId());
        entity.setAge(dto.getAge());
        entity.setLookingFor(dto.getLookingFor());
        entity.setHeight(dto.getHeight());
        entity.setEatingHabits(dto.getEatingHabits());
        entity.setCountryLivingIn(dto.getCountryLivingIn());
        entity.setCityLivingIn(dto.getCityLivingIn());
        entity.setComplexion(dto.getComplexion());
        entity.setReligion(dto.getReligion());
        entity.setCaste(dto.getCaste());
        entity.setEducation(dto.getEducation());
        entity.setMangal(dto.getMangal());
        entity.setResidentStatus(dto.getResidentStatus());
        entity.setPartnerOccupation(dto.getPartnerOccupation());
        entity.setPartnerIncome(dto.getPartnerIncome());
        entity.setUser(user);

        return entity;
    }
}
