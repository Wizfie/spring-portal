package com.ms.springms.model.uploads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFilesDTO {

    private Long filesId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
    private String uploadedBy;
    private Long stageId;
    private Long registrationId;
    private String approvalStatus;
    private String description;
}
