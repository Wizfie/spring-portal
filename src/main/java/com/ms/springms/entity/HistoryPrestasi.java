package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history_prestasi")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryPrestasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_award")
    private Event event;


    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    @Column(name = "prestasi")
    private String prestasi;

}
