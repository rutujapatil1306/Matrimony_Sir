package com.spring.jwt.ExpressInterest;

import com.spring.jwt.profile.UserProfileDTO2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressInterestWithProfileDTO {

    private Long interestId;

    private Integer fromUserId;
    private Integer toUserId;

    private String status;
    private String createdAt;

    private UserProfileDTO2 fromUserProfile;  // Sender profile
}
