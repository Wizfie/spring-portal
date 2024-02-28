package com.ms.springms.service.stages;

import com.ms.springms.entity.EventStages;
import com.ms.springms.repository.stage.EventStagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventStageService {
    @Autowired
    private EventStagesRepository eventStagesRepository;

    public EventStages createStage(EventStages eventStages) throws IllegalArgumentException {
        try {
            if (eventStages.getStageName() == null || eventStages.getStageName().isEmpty()){
                throw new IllegalArgumentException("Stage Name Cannot Be Blank");
            }
            return eventStagesRepository.save(eventStages);
        } catch (IllegalArgumentException ex){
            // Log the error
            throw ex; // Rethrow the IllegalArgumentException
        } catch (Exception ex){
            // Log the error
            throw new RuntimeException("An unexpected error occurred", ex); // Wrap and rethrow the exception
        }
    }


}
