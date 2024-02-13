package com.ms.springms.controller;

import com.ms.springms.entity.MemberTeam;
import com.ms.springms.service.team.memberTeam.MemberTeamService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberTeamController {


    @Autowired
    private MemberTeamService memberTeamService;


    @PostMapping("/add")
    public ResponseEntity<?> addMember(@RequestBody MemberTeam memberTeam){

        try {
            MemberTeam addedMember =  memberTeamService.addMember(memberTeam);
            return ResponseEntity.status(HttpStatus.OK).body(addedMember);

        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/get-all")
    public List<MemberTeam> getAllMember(){
        return memberTeamService.getAllMember();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MemberTeam>> findAllByTeamId ( @PathVariable Long id){
        List<MemberTeam> memberTeams = memberTeamService.getByIdTeam(id);
        return ResponseEntity.ok(memberTeams);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MemberTeam> updateMemberTeam(@PathVariable Long id , @RequestBody MemberTeam memberTeam){
        try {
            MemberTeam updateMemberTeam = memberTeamService.updateMemberTeam(memberTeam);
            return ResponseEntity.ok(updateMemberTeam);

        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMemberTeamNotFound(Exception e){
        return e.getMessage();
    }
}
