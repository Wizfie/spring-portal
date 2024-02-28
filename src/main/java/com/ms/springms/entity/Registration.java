package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;



    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL)
    private List<UploadFiles> uploadFiles;

    private String RegistrationStatus;
}
