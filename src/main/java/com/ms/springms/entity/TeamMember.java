package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String memberName;

    private String memberPosition;
}
