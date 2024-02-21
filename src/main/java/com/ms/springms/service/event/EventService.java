package com.ms.springms.service.event;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Event;
import com.ms.springms.repository.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


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


    public List<Event> getAllAwards(){
        return eventRepository.findAll();
    }
}
