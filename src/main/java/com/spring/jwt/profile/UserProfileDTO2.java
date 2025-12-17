package com.spring.jwt.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO2{

        private Integer userProfileId;

        // Basic Info
        private String firstName;
        private String middleName;
        private String lastName;
        private Integer age;
        private String gender;   // Enum(Gender) as String for API usage
        private String status;   // Enum(Status) as String

        // Contact
        private String address;
        private String taluka;
        private String district;
        private Integer pinCode;
        private String mobileNumber;
        private String mail;

        // Personal Details
        private String religion;
        private String caste;
        private String maritalStatus;
        private Double height;
        private Integer weight;
        private String bloodGroup;
        private String complexion;
        private String diet;

        // Health
        private Boolean spectacle;
        private Boolean lens;
        private Boolean physicallyChallenged;

        // Location
        private String homeTownDistrict;
        private String nativeTaluka;
        private String currentCity;

        // Other
        private String userProfileCol;

        // Relationship
        private Integer userId;
    }

