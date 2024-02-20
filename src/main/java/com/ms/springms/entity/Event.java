package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(name = "eventName", unique = true )
    private String eventName;

    @Column(name = "event_year")
    private String eventYear;

//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
//    private List<Step> steps;






}
