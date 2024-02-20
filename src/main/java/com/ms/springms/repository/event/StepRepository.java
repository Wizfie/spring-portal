package com.ms.springms.repository.event;

import com.ms.springms.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step , Long> {
}
