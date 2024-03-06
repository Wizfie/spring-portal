package com.ms.springms.model.registration;

import com.ms.springms.model.uploads.UploadFilesDTO;
import com.ms.springms.model.event.EventDTO;
import com.ms.springms.model.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {

    private Long registrationId;
    private String registrationStatus;
    private TeamDTO team;
    private EventDTO event;
    private List<UploadFilesDTO> uploadFiles; // Menambahkan atribut untuk menyimpan informasi upload files


}
