package com.ms.springms.repository;

import com.ms.springms.entity.EventStages;
import com.ms.springms.entity.Registration;
import com.ms.springms.entity.UploadFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository  extends JpaRepository<UploadFiles , Long> {


    List<UploadFiles> findByRegistration(Registration registration);

    List<UploadFiles> findByEventStages(EventStages eventStage);
}
