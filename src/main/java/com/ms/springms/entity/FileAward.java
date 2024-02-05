package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file_award")
public class FileAward {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_file")
    private String nameFile;


    @Column(name = "type_file")
    private String typeFile;


    @Column(name = "path_file")
    private String pathFile;


    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

}
