package com.spring.jwt.CompleteProfile;


import com.spring.jwt.entity.CompleteProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CompleteProfileRepository extends JpaRepository<CompleteProfile, Integer> {
    Optional<CompleteProfile> findByUser_Id(Integer userId);
}