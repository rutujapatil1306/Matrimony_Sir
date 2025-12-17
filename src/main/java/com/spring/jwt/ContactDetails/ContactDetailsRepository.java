package com.spring.jwt.ContactDetails;

import com.spring.jwt.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Integer> {

    Optional<ContactDetails> findByUser_Id(Integer userId);
}
