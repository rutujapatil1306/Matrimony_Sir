package com.spring.jwt.PartnerPreference;


import com.spring.jwt.entity.PartnerPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PartnerPreferenceRepository extends JpaRepository<PartnerPreference, Integer> {
    boolean existsByUser_Id(Integer userId);
    Optional<PartnerPreference> findByUser_Id(Integer userId);
}
