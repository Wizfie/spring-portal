package com.ms.springms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "upload_files")
public class UploadFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filesId;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private EventStages eventStages;

    @ManyToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    private String fileName;

    private String filePath;

    private String uploadedBy;

    private LocalDateTime uploadedAt;
}

