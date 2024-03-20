package com.ms.springms.service.event;

import com.ms.springms.Exceptions.ResourceNotFoundException;
import com.ms.springms.entity.Event;
import com.ms.springms.entity.Step;
import com.ms.springms.repository.event.EventRepository;
import com.ms.springms.repository.event.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepService {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private EventRepository eventRepository;

    public Step addStep(Long eventId, Step step) throws ResourceNotFoundException, IllegalArgumentException {

        // Memeriksa apakah objek step kosong
        if (step == null || step.getStepName() == null || step.getStartDate() == null || step.getEndDate() == null  ) {
            throw new IllegalArgumentException("Step cannot be null or have null attributes");
        }

        // Mencari event berdasarkan ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        step.setEvent(event);

        return stepRepository.save(step);
    }
}


