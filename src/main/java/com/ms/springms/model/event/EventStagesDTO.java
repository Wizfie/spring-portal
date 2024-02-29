package com.ms.springms.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventStagesDTO {
    private Long stageId;
    private String stageName;
    private String description;
    private String approval;

}
