package com.spring.jwt.EducationAndProfession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationAndProfessionDTO {

    private Integer educationId;

    private String education;
    private String degree;
    private String occupation;
    private String occupationDetails;
    private Integer incomePerYear;

    private String educationAndProfessionalDetailsCol;

    private Integer userId;  // link to User entity
}
