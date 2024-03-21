package com.ms.springms.service.team;

import com.ms.springms.entity.Event;
import com.ms.springms.entity.Team;
import com.ms.springms.entity.TeamMember;
import com.ms.springms.model.event.EventWithTeam;
import com.ms.springms.model.team.TeamEventWithMemberDTO;
import com.ms.springms.model.team.TeamMemberDTO;
import com.ms.springms.model.team.TeamWithMember;
import com.ms.springms.repository.team.TeamMemberRepository;
import com.ms.springms.repository.team.TeamRepository;
import com.ms.springms.service.event.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventService eventService;

    public Team createTeam(Team team){
        try {
            return teamRepository.save(team);
        } catch (DataIntegrityViolationException ex){
            throw  new IllegalArgumentException("Team Already Exist");
        }
    }

    public List<Team> getTeam(){
        return teamRepository.findAll();
    }
    public List<TeamWithMember> getAllTeam() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(team -> {
            List<TeamMember> members = teamMemberRepository.findByTeamTeamId(team.getTeamId());
            return new TeamWithMember(team, members);

        }).collect(Collectors.toList());
    }


    public List<TeamEventWithMemberDTO> getAllTeamWithEventAndTeamMember(){
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TeamEventWithMemberDTO convertToDTO(Team team) {
        TeamEventWithMemberDTO teamDTO = modelMapper.map(team, TeamEventWithMemberDTO.class);

        List<EventWithTeam> eventDTOs = new ArrayList<>();

        // Kelompokan Team Berdasarkan Event
        Map<Event, List<TeamMember>> memberByEvent = team.getMembers().stream().collect(Collectors.groupingBy(TeamMember::getEvent));

        for (Map.Entry<Event, List<TeamMember>> entry : memberByEvent.entrySet()) {
            Event event = entry.getKey();
            List<TeamMember> members = entry.getValue();

            EventWithTeam eventWithTeam = new EventWithTeam();
            eventWithTeam.setEventId(event.getEventId());
            eventWithTeam.setEventName(event.getEventName());

            List<TeamMemberDTO> memberDTOs = members.stream()
                    .map(member -> modelMapper.map(member, TeamMemberDTO.class))
                    .collect(Collectors.toList());

            eventWithTeam.setMembers(memberDTOs);
            eventDTOs.add(eventWithTeam);
        }

        teamDTO.setTeamEvent(eventDTOs);
        return teamDTO;
    }


}
