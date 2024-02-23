package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "team_name" ,unique = true)
    private String teamName;

    private String userId;

    private String description;


}
