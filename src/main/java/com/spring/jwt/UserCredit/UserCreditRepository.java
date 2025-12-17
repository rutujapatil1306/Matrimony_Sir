package com.spring.jwt.UserCredit;

import com.spring.jwt.entity.UserCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCreditRepository extends JpaRepository<UserCredit, Integer> {
}
