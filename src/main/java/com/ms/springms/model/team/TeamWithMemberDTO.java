package com.ms.springms.model.team;

import lombok.Data;
import java.util.List;

@Data
public class TeamWithMemberDTO {
    private TeamWithTeamMembersDTO team;
    private List<TeamMemberDTO> members;
}
