package com.spring.jwt.PartnerPreference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerPreferenceDTO {

    private Integer partnerPreferenceId;
    private String age;
    private String lookingFor;
    private String height;
    private String eatingHabits;
    private String countryLivingIn;
    private String cityLivingIn;
    private String complexion;
    private String religion;
    private String caste;
    private String education;
    private Boolean mangal;
    private String residentStatus;
    private String partnerOccupation;
    private Integer partnerIncome;
    private Integer userId;   // From @OneToOne User
}
