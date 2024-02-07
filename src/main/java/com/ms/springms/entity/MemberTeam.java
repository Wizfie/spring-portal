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


    private String position;


    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;




}
