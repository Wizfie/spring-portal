package com.ms.springms.model.team;

import com.ms.springms.entity.Team;
import com.ms.springms.entity.TeamMember;
import com.ms.springms.repository.team.TeamMemberRepository;
import com.ms.springms.repository.team.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamWithMember {

    private Team team;
    private List<TeamMember> members;
}
