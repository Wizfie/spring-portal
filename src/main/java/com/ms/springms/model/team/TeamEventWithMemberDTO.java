package com.ms.springms.model.team;

import com.ms.springms.model.event.EventWithTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamEventWithMemberDTO {
    private Long teamId;
    private String teamName;
    private String userId;
    private List<EventWithTeam> teamEvent;


}
