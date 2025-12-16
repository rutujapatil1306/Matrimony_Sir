package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class CompleteProfile {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer completeProfileId;

    @Column(length = 45)
    private String statusCol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userProfileId")
    private UserProfile userProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horoscopeDetailsId")
    private HoroscopeDetails horoscopeDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "educationId")
    private EducationAndProfession educationAndProfession;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyBackgroundId")
    private FamilyBackground familyBackground;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expectationsId")
    private PartnerPreference partnerPreference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contactId")
    private ContactDetails contactDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentId")
    private Document document;

    @Column(name = "profile_completed")
    private boolean profileCompleted = false;

    @Column(columnDefinition = "LONGTEXT")
    private String documentIds;

    @OneToMany(mappedBy = "completeProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
