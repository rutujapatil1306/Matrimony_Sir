package com.spring.jwt.FamilyBackground;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyBackgroundDTO {

    private Integer familyBackgroundId;
    private String fathersName;
    private String fatherOccupation;
    private String mothersName;
    private String motherOccupation;
    private Integer brother;
    private Integer marriedBrothers;
    private Integer sisters;
    private Integer marriedSisters;
    private Boolean interCasteInFamily;
    private String parentResiding;
    private String familyWealth;
    private String mamaSurname;
    private String mamaPlace;
    private String familyBackgroundCol;
    private String relativeSurnames;
    private Integer userId;   // link to User table
}
