package com.ms.springms.repository.stage;

import com.ms.springms.entity.Event;
import com.ms.springms.entity.EventStages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStagesRepository extends JpaRepository<EventStages, Long> {
    List<EventStages> findByEvent(Event event);
}
