package com.spring.jwt.ContactDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetailsDTO {

    private Integer contactId;

    private String fullAddress;
    private String city;
    private Integer pinCode;

    private String mobileNumber;
    private String alternateNumber;

    private Integer userId;   // from @OneToOne User
}
