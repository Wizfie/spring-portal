package com.ms.springms.controller;

import com.ms.springms.entity.TeamMember;
import com.ms.springms.service.team.TeamMemberService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;


    @PostMapping("/create")
    public ResponseEntity<?> createTeamMember(@RequestBody TeamMember teamMember) {
        try {
            TeamMember create = teamMemberService.createTeamMember(teamMember);
            return new ResponseEntity<>(create, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
