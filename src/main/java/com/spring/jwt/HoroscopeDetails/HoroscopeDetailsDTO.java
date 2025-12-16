package com.spring.jwt.HoroscopeDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoroscopeDetailsDTO {

    private Integer horoscopeDetailsId;

    private Date dob;
    private String time;
    private String birthPlace;
    private String rashi;
    private String nakshatra;
    private String charan;
    private String nadi;
    private String gan;
    private String mangal;
    private String gotra;
    private String devak;

    private Integer userId;
}
