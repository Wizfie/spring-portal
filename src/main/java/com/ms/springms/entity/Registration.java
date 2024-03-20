package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;
import java.util.List;

@Entity
@Data
@Table(name = "registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    private String createdBy;

    @Column(columnDefinition = "YEAR")
    private Year createdAt;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String RegistrationStatus;
}
