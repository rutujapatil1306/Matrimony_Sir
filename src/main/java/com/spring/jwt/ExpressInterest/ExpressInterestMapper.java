package com.spring.jwt.ExpressInterest;

import com.spring.jwt.entity.ExpressInterest;
import com.spring.jwt.entity.User;

public final class ExpressInterestMapper {

    private ExpressInterestMapper() {}

    public static ExpressInterestDTO toDTO(ExpressInterest e) {
        return new ExpressInterestDTO(
                e.getInterestId(),
                e.getFromUser().getId(),
                e.getToUserId(),
                e.getStatus(),
                e.getCreatedAt()
        );
    }

    public static ExpressInterest toEntity(ExpressInterestDTO dto, User fromUser) {
        ExpressInterest e = new ExpressInterest();
        e.setInterestId(dto.getInterestId());
        e.setFromUser(fromUser);
        e.setToUserId(dto.getToUserId());
        e.setStatus(dto.getStatus());
        e.setCreatedAt(dto.getCreatedAt());
        return e;
    }
}
