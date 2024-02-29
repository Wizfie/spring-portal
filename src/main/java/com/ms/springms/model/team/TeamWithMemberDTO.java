package com.ms.springms.model.team;

import lombok.Data;
import java.util.List;

@Data
public class TeamWithMemberDTO {
    private TeamDTO team;
    private List<TeamMemberDTO> members;
}
