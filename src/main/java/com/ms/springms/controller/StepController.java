package com.ms.springms.controller;

import com.ms.springms.entity.Step;
import com.ms.springms.service.event.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/step")
public class StepController {

    @Autowired
    private StepService stepService;

    @PostMapping("/add/{eventId}")
    public ResponseEntity<Step> addStep(@PathVariable Long eventId , @RequestBody Step step){

        Step addedStep = stepService.addStep(eventId, step);
        return new ResponseEntity<>(addedStep, HttpStatus.CREATED);
    }

}
