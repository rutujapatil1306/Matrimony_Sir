package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PartnerPreference {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partnerPreferenceId;

    @Column(nullable = false)
    private String age;

    @Column(length = 45, nullable = false)
    private String lookingFor;

    @Column(nullable = false)
    private String height;

    @Column(length = 45, nullable = false)
    private String eatingHabits;

    @Column(length = 45,nullable = false)
    private String countryLivingIn;

    @Column(length = 45, nullable = false)
    private String cityLivingIn;

    @Column(length = 45 ,nullable = false)
    private String complexion;

    @Column(length = 45 ,nullable = false)
    private String religion;

    @Column(length = 45 ,nullable = false)
    private String caste;

    @Column(length = 45, nullable = false)
    private String education;

    @Column(nullable = false)
    private Boolean mangal;

    @Column(length = 45, nullable = false)
    private String residentStatus;

    @Column(length = 45, nullable = false)
    private String partnerOccupation;

    @Column(nullable = false)
    private Integer partnerIncome;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "partnerPreference")
    private CompleteProfile status;

}
