package com.ms.springms.service.team.memberTeam;

import com.ms.springms.entity.MemberTeam;
import com.ms.springms.entity.Team;
import com.ms.springms.repository.event.EventRepository;
import com.ms.springms.repository.team.TeamRepository;
import com.ms.springms.repository.team.memberTeam.MemberTeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberTeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberTeamRepository memberTeamRepository;

    @Autowired
    private EventRepository eventRepository;


    public MemberTeam addMember(MemberTeam memberTeam) {


        String nameMember = memberTeam.getNameMember();
        Long idTeam = memberTeam.getIdTeam();
        String position = memberTeam.getPosition();

        try {

            MemberTeam newMember = new MemberTeam();

            newMember.setNameMember(nameMember);
            newMember.setPosition(position);
            newMember.setIdTeam(idTeam);

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

    public List<MemberTeam> getByIdTeam(Long idTeam){
        return memberTeamRepository.findAllByTeamId(idTeam);
    }


    @Transactional
    public MemberTeam updateMemberTeam(MemberTeam memberTeam){
//        Check MemberExist
        Optional<MemberTeam> existingMember = memberTeamRepository.findById(memberTeam.getMemberId());
        if (existingMember.isEmpty()){
            throw  new IllegalArgumentException("Member with Id" + memberTeam.getMemberId() + "Not Found");
        }

//        update member
        MemberTeam exitingMemberTeam = existingMember.get();
        exitingMemberTeam.setNameMember(memberTeam.getNameMember());
        exitingMemberTeam.setPosition(memberTeam.getPosition());
        exitingMemberTeam.setTeam(memberTeam.getTeam());

        return memberTeamRepository.save(exitingMemberTeam);
    }
}