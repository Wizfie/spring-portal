package com.ms.springms.controller.event;

import com.ms.springms.entity.Step;
import com.ms.springms.model.event.EventWithSteps;
import com.ms.springms.service.event.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/step")
public class StepController {

    @Autowired
    private StepService stepService;

    @PostMapping("/add/{eventId}")
    public ResponseEntity<Step> addStep(@PathVariable Long eventId , @RequestBody Step step) throws Exception {

        Step addedStep = stepService.addStep(eventId, step);
        return new ResponseEntity<>(addedStep, HttpStatus.CREATED);
    }


}
