package com.ms.springms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "history_presstasi")
public class HistoryPrestasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_award")
    private Awards awards;


    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @Column(name = "prestasi")
    private String prestasi;

}
