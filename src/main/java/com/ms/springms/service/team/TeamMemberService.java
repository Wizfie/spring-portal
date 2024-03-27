package com.ms.springms.service.team;

import com.ms.springms.Exceptions.ResourceNotFoundException;
import com.ms.springms.entity.TeamMember;
import com.ms.springms.model.team.UpdateMemberRequest;
import com.ms.springms.model.utils.Response;
import com.ms.springms.repository.team.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {


    @Autowired
    private TeamMemberRepository teamMemberRepository;


    public TeamMember createTeamMember(TeamMember member) throws Exception {
        if (member.getMemberName() == null || member.getMemberName().isEmpty()) {
            throw new IllegalArgumentException("Member name cannot be null or empty");
        }

        return teamMemberRepository.save(member);
    }

    public ResponseEntity<?> deleteTeamMember(Long teamMemberId) {
        try {
            TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
                    .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

            teamMemberRepository.delete(teamMember);
            return ResponseEntity.ok("Member with ID: " + teamMemberId + " successfully deleted");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting member: " + ex.getMessage());
        }
    }


    public Response updateTeamMember(Long teamMemberId, UpdateMemberRequest request) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(teamMemberId);
        if (optionalTeamMember.isPresent()) {
            TeamMember existingTeamMember = optionalTeamMember.get();
            // Validasi input
            if (request.getMemberName() == null || request.getMemberName().isEmpty() ||
                    request.getMemberPosition()  == null || request.getMemberPosition().isEmpty()) {
                throw new IllegalArgumentException("Member name or Member Position cannot be empty");
            }
            // Update data anggota tim
            existingTeamMember.setMemberName(request.getMemberName());
            existingTeamMember.setMemberPosition(request.getMemberPosition());

            TeamMember result = teamMemberRepository.save(existingTeamMember);
            return new Response("Success update data member " , result);
        } else {
            return  new Response("Team member not found with id: " + teamMemberId);
        }
    }

}
