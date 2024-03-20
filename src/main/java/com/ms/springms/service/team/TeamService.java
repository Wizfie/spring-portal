package com.ms.springms.service.team;

import com.ms.springms.entity.Team;
import com.ms.springms.entity.TeamMember;
import com.ms.springms.model.team.TeamWithMember;
import com.ms.springms.repository.team.TeamMemberRepository;
import com.ms.springms.repository.team.TeamRepository;
import com.ms.springms.repository.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private EventRepository eventRepository;

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

}
