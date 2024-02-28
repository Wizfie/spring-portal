package com.ms.springms.repository.stage;

import com.ms.springms.entity.EventStages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStagesRepository extends JpaRepository<EventStages, Long> {
}
