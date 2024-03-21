package com.ms.springms.service.event;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Event;
import com.ms.springms.entity.Step;
import com.ms.springms.model.event.EventWithSteps;
import com.ms.springms.repository.event.EventRepository;
import com.ms.springms.repository.event.StepRepository;
import com.ms.springms.service.team.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private StepRepository stepRepository;

    public Event createEvent(Event event) {

        String eventName = event.getEventName();
        String eventYear = event.getEventYear();

        try {
            if (eventName != null && !eventName.isBlank()) {

                Event newEvent = new Event();

                newEvent.setEventName(eventName);
                newEvent.setEventYear(eventYear);
              Event result =  eventRepository.save(newEvent);

                System.out.println(event);

                return result;
            } else {
                throw new IllegalArgumentException("Name Event Cannot blank");
            }

        } catch (DuplicateEntryException ex) {

            if (ex.getMessage().contains("Duplicate Entry")) {
                System.out.println("Duplicate Entry" + eventName);
                throw new DuplicateEntryException("Award Already Exist");
            } else {
                System.out.println("Data integrity violation exception: " + ex.getMessage());
                throw new RuntimeException("Error: Unable to Register. Please try again later.") ;
            }

        }
    }

    public List<EventWithSteps> getAllEventsWithSteps() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> {
            List<Step> steps = stepRepository.findByEventEventId(event.getEventId());
            return new EventWithSteps(event, steps);
        }).collect(Collectors.toList());
    }



    public List<Event> getAllAwards(){
        return eventRepository.findAll();
    }
}

