





package com.spring.jwt.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "educationAndProfession")
@Getter
@Setter
public class EducationAndProfession {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer educationId;

    @Column(length = 45, nullable = false)
    private String education;

    @Column(length = 45, nullable = false)
    private String degree;

    @Column(length = 45, nullable = false)
    private String occupation;

    @Column(length = 45, nullable = false)
    private String occupationDetails;

    @Column(length = 45, nullable = false)
    private Integer incomePerYear;

  //  @Column(length = 45)
   // private String status;

    @Column(length = 45)
    private String educationAndProfessionalDetailsCol;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "educationAndProfession")
    private CompleteProfile CompleteProfile;

}