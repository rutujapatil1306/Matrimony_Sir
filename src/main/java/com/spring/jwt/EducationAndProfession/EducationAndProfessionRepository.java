package com.spring.jwt.EducationAndProfession;

import com.spring.jwt.entity.EducationAndProfession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EducationAndProfessionRepository extends JpaRepository<EducationAndProfession, Integer> {
    boolean existsByUser_Id(Integer userId);
    Optional<EducationAndProfession> findByUser_Id(Integer userId);
}
