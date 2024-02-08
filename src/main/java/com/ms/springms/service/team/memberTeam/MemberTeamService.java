package com.ms.springms.service.team.memberTeam;

import com.ms.springms.entity.Awards;
import com.ms.springms.entity.MemberTeam;
import com.ms.springms.entity.Team;
import com.ms.springms.repository.team.TeamRepository;
import com.ms.springms.repository.team.memberTeam.MemberTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberTeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberTeamRepository memberTeamRepository;


    public MemberTeam addMember(MemberTeam memberTeam) {


        String nameMember = memberTeam.getNameMember();
        Long idTeam = memberTeam.getIdTeam();
        String position = memberTeam.getPosition();

        try {

            MemberTeam newMember = new MemberTeam();

            newMember.setNameMember(nameMember);
            newMember.setPosition(position);

            Team team = teamRepository.findById(idTeam).orElse(null);

            if (team != null){
                newMember.setTeam(team);

                memberTeamRepository.save(newMember);
                return newMember;
            } else {
                throw new IllegalArgumentException("Error add  member");
            }



        } catch (Exception ex){
            throw new RuntimeException("error add member " + ex.getMessage());

        }

    }

    public List<MemberTeam> getAllMember(){
        return memberTeamRepository.findAll();
    }
}