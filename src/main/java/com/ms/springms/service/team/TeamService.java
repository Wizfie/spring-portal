package com.ms.springms.service.team;

import com.ms.springms.entity.Team;
import com.ms.springms.repository.TeamRepository;
import com.ms.springms.repository.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ResponseEntity<?> getAllTeam(){
        try {
            List<Team> teams = teamRepository.findAll();
            if (teams.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found");
            } else {
                return ResponseEntity.ok(teams);
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Fetch Data");
        }
    }

}
