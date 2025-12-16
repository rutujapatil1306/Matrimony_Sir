package com.spring.jwt.ExpressInterest;


import java.util.List;

public interface ExpressInterestService {

    ExpressInterestDTO create(ExpressInterestDTO dto);

    ExpressInterestDTO getById(Long id);

    List<ExpressInterestDTO> getSent(Integer userId);

    List<ExpressInterestDTO> getReceived1(Integer userId);

    ExpressInterestDTO updateStatus(Long id, String status);

    void delete(Long id);

    List<ExpressInterestDTO> getSent(Integer userId, String status);

    List<ExpressInterestWithProfileDTO> getReceived(Integer userId, String status);

    InterestCountDTO getInterestSummary(Integer userId);

}

