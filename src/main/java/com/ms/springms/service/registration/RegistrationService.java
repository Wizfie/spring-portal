package com.ms.springms.service.registration;

import com.ms.springms.Exceptions.ResourceNotFoundException;
import com.ms.springms.entity.*;
import com.ms.springms.model.event.EventWithStages;
import com.ms.springms.model.team.TeamWithTeamMembersDTO;
import com.ms.springms.model.uploads.UploadFilesDTO;
import com.ms.springms.model.event.EventStagesDTO;
import com.ms.springms.model.registration.RegistrationRequest;
import com.ms.springms.model.registration.RegistrationResponseDTO;
import com.ms.springms.model.team.TeamMemberDTO;
import com.ms.springms.repository.RegistrationRepository;
import com.ms.springms.repository.UploadFileRepository;
import com.ms.springms.repository.event.EventRepository;
import com.ms.springms.repository.stage.EventStagesRepository;
import com.ms.springms.repository.team.TeamMemberRepository;
import com.ms.springms.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventStagesRepository eventStagesRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    public void registration(RegistrationRequest registrationRequest) {
        Long teamId = registrationRequest.getTeamId();
        Long eventId = registrationRequest.getEventId();
        String createdBy = registrationRequest.getCreatedBy();

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found" + teamId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found " + eventId));

        Registration registration = new Registration();
        registration.setTeam(team);
        registration.setEvent(event);
        registration.setCreatedBy(createdBy);
        registration.setCreatedAt(Year.now());
        registration.setRegistrationStatus("Pending");
        registrationRepository.save(registration);
    }

    public List<RegistrationResponseDTO> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        List<RegistrationResponseDTO> responseDTOList = new ArrayList<>();

        for (Registration registration : registrations) {
            RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
            responseDTO.setRegistrationId(registration.getRegistrationId());
            responseDTO.setRegistrationStatus(registration.getRegistrationStatus());
            responseDTO.setCreatedBy(registration.getCreatedBy());
            responseDTO.setCreatedAt(registration.getCreatedAt());

            // Set TeamWithTeamMembersDTO
            Team team = registration.getTeam();
            Event eventTeam = registration.getEvent();
            List<TeamMember> teamMembers = teamMemberRepository.findByTeamAndEvent(team ,eventTeam);
            TeamWithTeamMembersDTO teamWithTeamMembersDTO = new TeamWithTeamMembersDTO();
            teamWithTeamMembersDTO.setTeamId(team.getTeamId());
            teamWithTeamMembersDTO.setTeamName(team.getTeamName());
            teamWithTeamMembersDTO.setUserId(team.getUserId());
            teamWithTeamMembersDTO.setTeamMember(convertToTeamMemberDTOList(teamMembers));

            // Set EventWithStages
            Event event = registration.getEvent();
            List<EventStages> eventStages = eventStagesRepository.findByEvent(event);
            EventWithStages eventWithStages = new EventWithStages();
            eventWithStages.setEventId(event.getEventId());
            eventWithStages.setEventName(event.getEventName());
            eventWithStages.setStages(convertToEventStagesDTOList(eventStages));

            responseDTO.setTeam(teamWithTeamMembersDTO);
            responseDTO.setEvent(eventWithStages);

            // Ambil semua upload files yang terkait dengan registrasi tertentu
            List<UploadFiles> uploadFiles = uploadFileRepository.findByRegistration(registration);
            List<UploadFilesDTO> registrationUploadFiles = new ArrayList<>();
            for (UploadFiles uploadFile : uploadFiles) {
                UploadFilesDTO uploadFileDTO = new UploadFilesDTO();
                uploadFileDTO.setFilesId(uploadFile.getFilesId());
                uploadFileDTO.setFileName(uploadFile.getFileName());
                uploadFileDTO.setFilePath(uploadFile.getFilePath());
                uploadFileDTO.setUploadedAt(uploadFile.getUploadedAt());
                uploadFileDTO.setUploadedBy(uploadFile.getUploadedBy());
                uploadFileDTO.setStageId(uploadFile.getEventStages().getStageId());
                uploadFileDTO.setRegistrationId(registration.getRegistrationId());
                uploadFileDTO.setApprovalStatus(uploadFile.getApprovalStatus());
                registrationUploadFiles.add(uploadFileDTO);
            }

            // Tambahkan upload files terkait dengan registrasi ke dalam responsenya
            responseDTO.setUploadFiles(registrationUploadFiles);

            responseDTOList.add(responseDTO);
        }

        return responseDTOList;
    }

    public RegistrationResponseDTO getRegistrationById(Long registrationId) {
        try {
            Optional<Registration> registrationOptional = registrationRepository.findById(registrationId);

            if (registrationOptional.isPresent()) {
                Registration registration = registrationOptional.get();
                RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
                responseDTO.setRegistrationId(registration.getRegistrationId());
                responseDTO.setRegistrationStatus(registration.getRegistrationStatus());
                responseDTO.setCreatedBy(registration.getCreatedBy());
                responseDTO.setCreatedAt(registration.getCreatedAt());

                // Set TeamWithTeamMembersDTO
                Team team = registration.getTeam();
                Event eventTeam = registration.getEvent();
                List<TeamMember> teamMembers = teamMemberRepository.findByTeamAndEvent(team,eventTeam);
                TeamWithTeamMembersDTO teamWithTeamMembersDTO = new TeamWithTeamMembersDTO();
                teamWithTeamMembersDTO.setTeamId(team.getTeamId());
                teamWithTeamMembersDTO.setTeamName(team.getTeamName());
                teamWithTeamMembersDTO.setUserId(team.getUserId());
                teamWithTeamMembersDTO.setTeamMember(convertToTeamMemberDTOList(teamMembers));

                // Set EventWithStages
                Event event = registration.getEvent();
                List<EventStages> eventStages = eventStagesRepository.findByEvent(event);
                EventWithStages eventWithStages = new EventWithStages();
                eventWithStages.setEventId(event.getEventId());
                eventWithStages.setEventName(event.getEventName());
                eventWithStages.setStages(convertToEventStagesDTOList(eventStages));


                // Ambil semua upload files yang terkait dengan registrasi tertentu
                List<UploadFiles> uploadFiles = uploadFileRepository.findByRegistration(registration);
                List<UploadFilesDTO> registrationUploadFiles = new ArrayList<>();
                for (UploadFiles uploadFile : uploadFiles) {
                    UploadFilesDTO uploadFileDTO = new UploadFilesDTO();
                    uploadFileDTO.setFilesId(uploadFile.getFilesId());
                    uploadFileDTO.setFileName(uploadFile.getFileName());
                    uploadFileDTO.setFilePath(uploadFile.getFilePath());
                    uploadFileDTO.setUploadedAt(uploadFile.getUploadedAt());
                    uploadFileDTO.setUploadedBy(uploadFile.getUploadedBy());
                    uploadFileDTO.setStageId(uploadFile.getEventStages().getStageId());
                    uploadFileDTO.setRegistrationId(registration.getRegistrationId());
                    uploadFileDTO.setApprovalStatus(uploadFile.getApprovalStatus());
                    uploadFileDTO.setDescription(uploadFile.getDescription());
                    registrationUploadFiles.add(uploadFileDTO);
                }

                // Tambahkan upload files terkait dengan registrasi ke dalam responsenya
                responseDTO.setUploadFiles(registrationUploadFiles);


                responseDTO.setTeam(teamWithTeamMembersDTO);
                responseDTO.setEvent(eventWithStages);



                return responseDTO;
            } else {
                // Registrasi dengan ID yang diberikan tidak ditemukan
                throw new ResourceNotFoundException("Registrasi dengan ID " + registrationId + " tidak ditemukan");
            }
        } catch (Exception ex) {
            // Tangani pengecualian dan kembalikan pesan kesalahan yang sesuai
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Terjadi kesalahan saat mencari registrasi dengan ID " + registrationId, ex);
        }
    }

    // Metode untuk mengonversi List<TeamMember> menjadi List<TeamMemberDTO>
    private List<TeamMemberDTO> convertToTeamMemberDTOList(List<TeamMember> teamMembers) {
        List<TeamMemberDTO> teamMemberDTOList = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
            teamMemberDTO.setTeamMemberId(teamMember.getTeamMemberId());
            teamMemberDTO.setMemberName(teamMember.getMemberName());
            teamMemberDTO.setMemberPosition(teamMember.getMemberPosition());
            teamMemberDTOList.add(teamMemberDTO);
        }
        return teamMemberDTOList;
    }

    // Metode untuk mengonversi List<EventStages> menjadi List<EventStagesDTO>
    private List<EventStagesDTO> convertToEventStagesDTOList(List<EventStages> eventStages) {
        List<EventStagesDTO> eventStagesDTOList = new ArrayList<>();
        for (EventStages eventStage : eventStages) {
            EventStagesDTO eventStagesDTO = new EventStagesDTO();
            eventStagesDTO.setStageId(eventStage.getStageId());
            eventStagesDTO.setStageName(eventStage.getStageName());
            eventStagesDTO.setDescription(eventStage.getDescription());

            eventStagesDTOList.add(eventStagesDTO);
        }
        return eventStagesDTOList;
    }

}
