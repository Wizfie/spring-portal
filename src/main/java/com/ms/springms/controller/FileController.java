package com.ms.springms.controller;


import com.ms.springms.entity.EventStages;
import com.ms.springms.entity.Registration;
import com.ms.springms.service.upload.UploadFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/file")
public class FileController {

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

    @PutMapping("/update/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable Long fileId,
                                             @RequestParam("file") MultipartFile file,
                                             @RequestParam("eventName") String eventName,
                                             @RequestParam("teamName") String teamName,
                                             @RequestParam EventStages eventStages,
                                             @RequestParam Registration registration) {
        try {
            uploadFilesService.updateFileById(fileId, file, eventName, teamName, eventStages, registration);
            return ResponseEntity.status(HttpStatus.OK).body("File berhasil diperbarui");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memperbarui file: " + e.getMessage());
        }
    }

    @PutMapping("/approve/{fileId}")
    public ResponseEntity<String> approveFile(@PathVariable Long fileId, @RequestParam String approvalStatus) {
        try {
            uploadFilesService.approveFile(fileId, approvalStatus);
            return ResponseEntity.ok("File approval status updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws MalformedURLException {
        Resource resource = uploadFilesService.downloadFile(fileName);

        String contentType = "application/octet-stream";
        try{
            contentType = resource.getURL().openConnection().getContentType();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\"" + resource.getFilename()+ "\"")
                .body(resource);
    }

}
