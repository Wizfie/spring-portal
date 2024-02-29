package com.ms.springms.model.registration;

import com.ms.springms.model.event.EventDTO;
import com.ms.springms.model.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {

    private Long registrationId;
    private String registrationStatus;
    private TeamDTO team;
    private EventDTO event;

}
