package com.ms.springms.controller.event;

import com.ms.springms.entity.EventStages;
import com.ms.springms.service.stages.EventStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event-stage")
public class EventStageController {

    @Autowired
    private EventStageService eventStageService;

    @PostMapping("/create")
    public ResponseEntity<?> createStage(@RequestBody EventStages eventStages){
        try {
            EventStages stages = eventStageService.createStage(eventStages);
            return new ResponseEntity<>(stages, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
