package com.ms.springms.controller;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Event;
import com.ms.springms.model.event.EventWithSteps;
import com.ms.springms.service.event.EventService;
import com.ms.springms.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {


    @Autowired
    private EventService eventService;


    @PostMapping("/create-event")
    public ResponseEntity<?> createAwards( @RequestBody Event event){

        try {
            Event result = eventService.createEvent(event);
            return  new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DuplicateEntryException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-event")
    public ResponseEntity<List<EventWithSteps>> getAllEventsWithSteps() {
        List<EventWithSteps> eventsWithSteps = eventService.getAllEventsWithSteps();
        return new ResponseEntity<>(eventsWithSteps, HttpStatus.OK);
    }


    @GetMapping("/get-all-awards")
    public List<Event> getAllAwards(){
        return eventService.getAllAwards();
    }

}
