package com.ms.springms.model.uploads;

import com.ms.springms.entity.EventStages;
import com.ms.springms.entity.Registration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequestDTO {
  private String teamName;
  private String eventName;
  private Long stageId;
  private Long registrationId;

}
