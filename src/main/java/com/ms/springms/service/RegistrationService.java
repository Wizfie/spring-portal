package com.ms.springms.service;

import com.ms.springms.entity.*;
import com.ms.springms.model.event.EventDTO;
import com.ms.springms.model.event.EventStagesDTO;
import com.ms.springms.model.registration.RegistrationRequest;
import com.ms.springms.model.registration.RegistrationResponseDTO;
import com.ms.springms.model.team.TeamDTO;
import com.ms.springms.model.team.TeamMemberDTO;
import com.ms.springms.repository.RegistrationRepository;
import com.ms.springms.repository.event.EventRepository;
import com.ms.springms.repository.stage.EventStagesRepository;
import com.ms.springms.repository.team.TeamMemberRepository;
import com.ms.springms.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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

    public void registration(RegistrationRequest registrationRequest){
        Long teamId = registrationRequest.getTeamId();
        Long eventId = registrationRequest.getEventId();

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Not Found" + teamId));
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found " + eventId));

        Registration registration = new Registration();
        registration.setTeam(team);
        registration.setEvent(event);
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

            // Set TeamDTO
            Team team = registration.getTeam();
            List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setTeamId(team.getTeamId());
            teamDTO.setTeamName(team.getTeamName());
            teamDTO.setUserId(team.getUserId());
            teamDTO.setTeamMember(convertToTeamMemberDTOList(teamMembers)); // Mengonversi List<TeamMember> menjadi List<TeamMemberDTO>

            // Set EventDTO
            Event event = registration.getEvent();
            List<EventStages> eventStages = eventStagesRepository.findByEvent(event);
            EventDTO eventDTO = new EventDTO();
            eventDTO.setEventId(event.getEventId());
            eventDTO.setEventName(event.getEventName());
            eventDTO.setStages(convertToEventStagesDTOList(eventStages)); // Mengonversi List<EventStages> menjadi List<EventStagesDTO>

            responseDTO.setTeam(teamDTO);
            responseDTO.setEvent(eventDTO);

            responseDTOList.add(responseDTO);
        }

        return responseDTOList;
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
            // Set properti lain jika ada
            eventStagesDTOList.add(eventStagesDTO);
        }
        return eventStagesDTOList;
    }
}
