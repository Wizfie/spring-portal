package com.ms.springms.controller.team;

import com.ms.springms.entity.Team;
import com.ms.springms.model.team.TeamWithMember;
import com.ms.springms.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;


    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody Team team){
        try {
            Team createdTeam = teamService.createTeam(team);
            return  new ResponseEntity<>(createdTeam , HttpStatus.CREATED);
        } catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public List<Team> getTeam() {
        return teamService.getTeam();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TeamWithMember>> getAllTeam(){
        List<TeamWithMember> teamWithMembers = teamService.getAllTeam();

         return new ResponseEntity<>(teamWithMembers,HttpStatus.OK);
    }

}
