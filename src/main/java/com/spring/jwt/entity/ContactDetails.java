package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "contactDetails")
@Getter
@Setter
public class ContactDetails {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactId;

    @Column(length = 45)
    private String fullAddress;

    @Column(length = 45, nullable = false)
    private String city;

    @Column(nullable = false)
    private Integer pinCode;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false)
    private String alternateNumber;

    @OneToOne(mappedBy = "contactDetails")
    private CompleteProfile CompleteProfile;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}