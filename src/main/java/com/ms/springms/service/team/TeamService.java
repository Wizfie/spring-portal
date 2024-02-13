package com.ms.springms.service.team;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Awards;
import com.ms.springms.entity.Team;
import com.ms.springms.model.Teams.MemberTeam.MemberDTO;
import com.ms.springms.model.Teams.TeamWithMembers;
import com.ms.springms.repository.AwardRepository;
import com.ms.springms.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AwardRepository awardRepository;

    public Team createTeam(Team team){

        String nameTeam = team.getNameTeam();
        Long idUser = team.getIdUser();

        try {
            if (nameTeam != null && !nameTeam.isBlank()){

                Team newTeam = new Team();

                newTeam.setNameTeam(nameTeam);
                newTeam.setIdUser(idUser);

                teamRepository.save(newTeam);
                return newTeam;

                }else {
                    throw  new IllegalArgumentException("error create Team") ;
            }
        } catch (DuplicateEntryException ex){
            if (ex.getMessage().contains("Duplicate Entry")){
                System.out.println("Duplicate Entry" + nameTeam);
                throw new  DuplicateEntryException("Team Already Exist");
            } else {
                System.out.println("Data integrity violation exception: " + ex.getMessage());
                    throw  new IllegalArgumentException("Error: Please try again later.") ;

            }
        }

    }

    public List<Team> getAllTeam(){
        try {
            return teamRepository.findAll();
        } catch (Exception ex){
            throw  new RuntimeException("Error get Team " + ex.getMessage());
        }
    }


    public List<TeamWithMembers> getTeamWithMembers(){
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(
                team -> {
                    TeamWithMembers dto = new TeamWithMembers();
                    dto.setNameTeam(team.getNameTeam());
                    dto.setIdUser(String.valueOf(team.getIdUser()));
                    dto.setIdTeam(team.getId());



                    List<MemberDTO> members = team.getMembers().stream()
                            .map(member -> {
                            MemberDTO memberDTO = new MemberDTO();
                            memberDTO.setId(member.getId());
                            memberDTO.setMemberName(member.getNameMember());
                            memberDTO.setPosition(member.getPosition());

                            return  memberDTO;
                            }).toList();
                    dto.setMembers(members);
                    return dto;
                }).collect(Collectors.toList());
    }
}
