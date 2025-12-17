package com.spring.jwt.profile;

import com.spring.jwt.dto.UserProfileDTO;
import com.spring.jwt.entity.User;
import com.spring.jwt.entity.UserProfile;
import com.spring.jwt.entity.Enums.Gender;
import com.spring.jwt.entity.Enums.Status;

public class UserProfileMapper {

    public static UserProfileDTO2 toDTO(UserProfile profile) {
        return new UserProfileDTO2(
                profile.getUserProfileId(),
                profile.getFirstName(),
                profile.getMiddleName(),
                profile.getLastName(),
                profile.getAge(),
                profile.getGender().name(),
                profile.getStatus().name(),
                profile.getAddress(),
                profile.getTaluka(),
                profile.getDistrict(),
                profile.getPinCode(),
                profile.getMobileNumber(),
                profile.getMail(),
                profile.getReligion(),
                profile.getCaste(),
                profile.getMaritalStatus(),
                profile.getHeight(),
                profile.getWeight(),
                profile.getBloodGroup(),
                profile.getComplexion(),
                profile.getDiet(),
                profile.getSpectacle(),
                profile.getLens(),
                profile.getPhysicallyChallenged(),
                profile.getHomeTownDistrict(),
                profile.getNativeTaluka(),
                profile.getCurrentCity(),
                profile.getUserProfileCol(),
                profile.getUser().getId()
        );
    }

    public static UserProfile toEntity(UserProfileDTO2 dto, User user) {
        UserProfile profile = new UserProfile();
        profile.setUserProfileId(dto.getUserProfileId());
        profile.setFirstName(dto.getFirstName());
        profile.setMiddleName(dto.getMiddleName());
        profile.setLastName(dto.getLastName());
        profile.setAge(dto.getAge());
        profile.setGender(Gender.valueOf(dto.getGender()));
        profile.setStatus(Status.valueOf(dto.getStatus()));
        profile.setAddress(dto.getAddress());
        profile.setTaluka(dto.getTaluka());
        profile.setDistrict(dto.getDistrict());
        profile.setPinCode(dto.getPinCode());
        profile.setMobileNumber(dto.getMobileNumber());
        profile.setMail(dto.getMail());
        profile.setReligion(dto.getReligion());
        profile.setCaste(dto.getCaste());
        profile.setMaritalStatus(dto.getMaritalStatus());
        profile.setHeight(dto.getHeight());
        profile.setWeight(dto.getWeight());
        profile.setBloodGroup(dto.getBloodGroup());
        profile.setComplexion(dto.getComplexion());
        profile.setDiet(dto.getDiet());
        profile.setSpectacle(dto.getSpectacle());
        profile.setLens(dto.getLens());
        profile.setPhysicallyChallenged(dto.getPhysicallyChallenged());
        profile.setHomeTownDistrict(dto.getHomeTownDistrict());
        profile.setNativeTaluka(dto.getNativeTaluka());
        profile.setCurrentCity(dto.getCurrentCity());
        profile.setUserProfileCol(dto.getUserProfileCol());
        profile.setUser(user);
        return profile;
    }

    public static UserProfilePublicDTO toPublicDTO(UserProfile p) {
        UserProfilePublicDTO dto = new UserProfilePublicDTO();

        dto.setUserProfileId(p.getUserProfileId());
        dto.setFirstName(p.getFirstName());
        dto.setAge(p.getAge());
        dto.setGender(p.getGender().name());
        dto.setReligion(p.getReligion());
        dto.setCaste(p.getCaste());
        dto.setHeight(p.getHeight());
        dto.setComplexion(p.getComplexion());
        dto.setCurrentCity(p.getCurrentCity());

        return dto;
    }
}
