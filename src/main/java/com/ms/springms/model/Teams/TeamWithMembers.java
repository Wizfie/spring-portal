package com.ms.springms.model.Teams;

import com.ms.springms.model.Teams.MemberTeam.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamWithMembers {

    private Long idTeam;
    private String nameTeam;
    private String idUser;
    private List<MemberDTO> members;

}
