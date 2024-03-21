package com.ms.springms.model.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamWithTeamMembersDTO {
    private Long teamId;
    private String teamName;
    private String userId;
    private List<TeamMemberDTO> teamMember;

    }
