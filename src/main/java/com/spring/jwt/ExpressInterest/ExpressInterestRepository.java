package com.spring.jwt.ExpressInterest;

import com.spring.jwt.entity.Enums.InterestStatus;
import com.spring.jwt.entity.ExpressInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpressInterestRepository extends JpaRepository<ExpressInterest, Long> {

    boolean existsByFromUser_IdAndToUserId(Integer fromUserId, Integer toUserId);

    List<ExpressInterest> findByFromUser_Id(Integer fromUserId);

    List<ExpressInterest> findByToUserId(Integer toUserId);

    List<ExpressInterest> findByFromUser_IdAndStatus(Integer fromUserId, InterestStatus status);

    List<ExpressInterest> findByToUserIdAndStatus(Integer toUserId, InterestStatus status);

    long countByFromUser_IdAndStatus(Integer userId, InterestStatus status);

    long countByToUserIdAndStatus(Integer userId, InterestStatus status);
}

