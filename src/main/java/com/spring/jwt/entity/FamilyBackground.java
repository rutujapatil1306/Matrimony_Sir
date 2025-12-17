package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "family_background")
@Getter
@Setter
public class FamilyBackground {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer familyBackgroundId;

    @Column(length = 45, nullable = false)
    private String fathersName;

    @Column(length = 45, nullable = false)
    private String fatherOccupation;

    @Column(length = 45, nullable = false)
    private String mothersName;

    @Column(length = 45, nullable = false)
    private String motherOccupation;

    @Column(nullable = false)
    private Integer brother;

    @Column(nullable = false)
    private Integer marriedBrothers;

    @Column(nullable = false)
    private Integer sisters;

    @Column(nullable = false)
    private Integer marriedSisters;

    @Column(length = 45, nullable = false)
    private Boolean interCasteInFamily;

    @Column(length = 45, nullable = false)
    private String parentResiding;

    @Column(length = 45)
    private String familyWealth;

    @Column(length = 45, nullable = false)
    private String mamaSurname;

    @Column(length = 45, nullable = false)
    private String mamaPlace;

    @Column(length = 45)
    private String familyBackgroundCol;

    @Column(length = 45)
    private String relativeSurnames;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "familyBackground")
    private CompleteProfile status;

}