package com.ms.springms.service.team;

import com.ms.springms.entity.Team;
import com.ms.springms.repository.TeamRepository;
import com.ms.springms.repository.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EventRepository eventRepository;

    public Team createTeam(Team team){
        try {
            return teamRepository.save(team);
        } catch (DataIntegrityViolationException ex){
            throw  new IllegalArgumentException("Team Already Exist");
        }
    }
}
