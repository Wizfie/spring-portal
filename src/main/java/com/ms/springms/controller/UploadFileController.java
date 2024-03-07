package com.ms.springms.controller;


import com.ms.springms.entity.EventStages;
import com.ms.springms.entity.Registration;
import com.ms.springms.model.uploads.UploadRequestDTO;
import com.ms.springms.repository.UploadFileRepository;
import com.ms.springms.service.UploadFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class UploadFileController {

    @Autowired
    private UploadFilesService uploadFilesService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam String eventName,
            @RequestParam String teamName,
            @RequestParam EventStages eventStages,
            @RequestParam Registration registration) {
        try {
            uploadFilesService.uploadFile(file,eventName,teamName,eventStages,registration);
            return ResponseEntity.status(HttpStatus.OK).body("File "+ file.getOriginalFilename()+ " uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

}
