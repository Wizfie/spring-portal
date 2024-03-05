package com.ms.springms.model.event;

import com.ms.springms.entity.UploadFiles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventStagesDTO {
    private Long stageId;
    private String stageName;
    private String description;
    List<UploadFiles> uploadFiles;

}
