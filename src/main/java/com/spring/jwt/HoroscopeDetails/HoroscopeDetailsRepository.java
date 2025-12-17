package com.spring.jwt.HoroscopeDetails;

import com.spring.jwt.entity.HoroscopeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoroscopeDetailsRepository extends JpaRepository<HoroscopeDetails, Integer> {
    boolean existsByUser_Id(Integer userId);

    Optional<HoroscopeDetails> findByUser_Id(Integer userId);
}
