package com.ms.springms.controller;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Team;
import com.ms.springms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/create-team")
    public ResponseEntity<?> createTeam(@RequestBody Team team){
        try {
            String result = teamService.createTeam(team);
            return new ResponseEntity<>(result , HttpStatus.OK);
        } catch (DuplicateEntryException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
