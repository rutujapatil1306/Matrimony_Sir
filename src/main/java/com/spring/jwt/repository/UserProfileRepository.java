package com.spring.jwt.repository;

import com.spring.jwt.entity.Enums.Gender;
import com.spring.jwt.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserId(Integer userId);


    List<UserProfile> findByGender(Gender g);

    Optional<UserProfile> findByUser_Id(Integer userId);

    boolean existsByUser_Id(Integer userId);

}