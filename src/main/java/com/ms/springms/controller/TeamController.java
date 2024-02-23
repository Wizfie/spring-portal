package com.ms.springms.controller;

import com.ms.springms.entity.Team;
import com.ms.springms.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
