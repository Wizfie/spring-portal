package com.ms.springms.controller;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Team;
import com.ms.springms.model.Teams.TeamWithMembers;
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
            String result = String.valueOf(teamService.createTeam(team));
            return new ResponseEntity<>(result , HttpStatus.OK);
        } catch (DuplicateEntryException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public List<Team> getAllTeam(){
        try {
           return teamService.getAllTeam();
        } catch (Exception ex){
            throw new RuntimeException("Error get team " + ex.getMessage());


        }
    }

    @GetMapping("/with-members")
    public ResponseEntity<List<TeamWithMembers>> getTeamsWithMembers() {
        try {
            List<TeamWithMembers> teamsWithMembers = teamService.getTeamWithMembers();
            return ResponseEntity.ok(teamsWithMembers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
