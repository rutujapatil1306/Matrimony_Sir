package com.spring.jwt.FamilyBackground;

import com.spring.jwt.entity.FamilyBackground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FamilyBackgroundRepository extends JpaRepository<FamilyBackground, Integer> {
    boolean existsByUser_Id(Integer userId);
    Optional<FamilyBackground> findByUser_Id(Integer userId);
}
