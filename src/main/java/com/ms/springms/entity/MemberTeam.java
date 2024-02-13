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
    private Long id;

    @Column(name = "name_member")
    private String nameMember;

    @ManyToOne
    @JoinColumn(name = "id_awards")
    private Awards awards;

    private String position;


    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    public Long getIdTeam(){return  team != null ? team.getId() : null;}

    public  Long getIdAwards(){return awards != null ? awards.getId(): null;}

    public void setIdTeam(Long idTeam){
        if (team == null){
            team = new Team();
        }
        team.setId(idTeam);
    }

    public void setIdAwards(Long idAwards){
        if (awards == null){
            awards = new Awards();
        }
        awards.setId(idAwards);
    }
}
