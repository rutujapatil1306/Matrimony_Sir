package com.spring.jwt.UserView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewDTO {

    private Integer userViewId;

    private LocalDate date;

    private Integer userId;          // FK → User

    private Integer contactNumberId; // FK → ContactDetails or similar
}
