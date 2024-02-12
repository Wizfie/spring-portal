package com.ms.springms.controller;

import com.ms.springms.entity.MemberTeam;
import com.ms.springms.service.team.memberTeam.MemberTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
