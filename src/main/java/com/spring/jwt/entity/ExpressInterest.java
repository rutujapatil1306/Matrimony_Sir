package com.spring.jwt.entity;
import com.spring.jwt.entity.Enums.InterestStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "express_interest")
@Data
public class ExpressInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;

    // who sent
    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    private Integer toUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterestStatus status;  // ACCEPTED, DECLINED, PENDING

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
