package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registration_event")
public class RegistrationEvent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "award_id")
    private Event event;

    @Column(name = "improvement_title")
    private String improvementTitle;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;





}
