package com.ms.springms.controller.team;

import com.ms.springms.entity.TeamMember;
import com.ms.springms.model.team.UpdateMemberRequest;
import com.ms.springms.model.utils.Response;
import com.ms.springms.service.team.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{teamMemberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long teamMemberId){
        try {
            ResponseEntity<?> response  = teamMemberService.deleteMember(teamMemberId);
            return ResponseEntity.ok().body(response.getBody());

        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTeamMember(@PathVariable("id") Long teamMemberId,
                                                   @RequestBody UpdateMemberRequest request) {
        try {
            Response response = teamMemberService.updateTeamMember(teamMemberId, request);
            return ResponseEntity.ok().body((response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Validation failed", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Data tidak di temukan",e.getMessage()));
        }
    }
}
