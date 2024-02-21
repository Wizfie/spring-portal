package com.ms.springms.model.event;

import com.ms.springms.entity.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String eventName;
    private String eventYear;
    private List<Step> steps;
}
