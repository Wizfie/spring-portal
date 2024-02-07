package com.ms.springms.service;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Awards;
import com.ms.springms.entity.Team;
import com.ms.springms.repository.AwardRepository;
import com.ms.springms.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AwardRepository awardRepository;

    public String createTeam(Team team){

        String nameTeam = team.getNameTeam();
        Long idAward = team.getIdAward();

        try {
            if (nameTeam != null && !nameTeam.isBlank()){

                Team newTeam = new Team();

                newTeam.setNameTeam(team.getNameTeam());



                Awards award = awardRepository.findById(idAward).orElse(null);

                if (award != null){
                    newTeam.setAwards(award);
                teamRepository.save(newTeam);
                return "Team Created";

                }else {
                    return "error id_award null";
                }
            }else {
                return "Error Created Team";
            }
        } catch (DuplicateEntryException ex){
            if (ex.getMessage().contains("Duplicate Entry")){
                System.out.println("Duplicate Entry" + nameTeam);
                throw new  DuplicateEntryException("Team Already Exist");
            } else {
                System.out.println("Data integrity violation exception: " + ex.getMessage());
                return "Error: Please try again later.";

            }
        }

    }

}
