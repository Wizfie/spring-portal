package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "awards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_award")
    private String nameAward;

    @Column(name = "date_award")
    private Date dateAward;



}
