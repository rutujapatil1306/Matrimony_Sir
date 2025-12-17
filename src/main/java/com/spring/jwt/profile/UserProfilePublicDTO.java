package com.spring.jwt.profile;

import lombok.Data;

@Data
public class UserProfilePublicDTO {

    private Integer userProfileId;

    private String firstName;
    private Integer age;
    private String gender;
    private String religion;
    private String caste;
    private Double height;
    private String complexion;
    private String currentCity;
}
