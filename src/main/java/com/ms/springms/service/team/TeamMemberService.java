package com.ms.springms.service.team;

import com.ms.springms.entity.TeamMember;
import com.ms.springms.model.team.TeamMemberDTO;
import com.ms.springms.repository.team.TeamMemberRepository;
import com.ms.springms.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamMemberService {


    @Autowired
    private TeamMemberRepository teamMemberRepository;


    public TeamMember createTeamMember(TeamMember teamMember) throws Exception {
        try {
            if (teamMember.getMemberName() == null || teamMember.getMemberName().isEmpty()) {
                throw new IllegalArgumentException("Member name cannot be null or empty");
            }

            return teamMemberRepository.save(teamMember);
        } catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

}
