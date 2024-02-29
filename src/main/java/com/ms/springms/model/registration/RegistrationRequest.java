package com.ms.springms.model.registration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private Long teamId;
    private Long eventId;
    private String registrationStatus;
}
