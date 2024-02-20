package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "name_member")
    private String nameMember;


    private String position;


    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    public Long getIdTeam(){return  team != null ? team.getId() : null;}


    public void setIdTeam(Long idTeam){
        if (team == null){
            team = new Team();
        }
        team.setId(idTeam);
    }

}
