package com.ms.springms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.springms.entity.EventStages;
import com.ms.springms.entity.Registration;
import com.ms.springms.entity.UploadFiles;
import com.ms.springms.model.uploads.UploadRequestDTO;
import com.ms.springms.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class UploadFilesService {

    @Autowired
    private UploadFileRepository uploadFileRepository;


    public void uploadFile(MultipartFile file ,String eventName , String teamName ,EventStages eventStages, Registration registration) throws IOException {


        // Pastikan file yang diunggah tidak kosong
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            String fileName = file.getOriginalFilename();
            String uploadDir = "./uploads/" + eventName + "/" + teamName + "/";
            Path uploadPath = Paths.get(uploadDir);

            // Membuat folder jika belum ada
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // Menyimpan detail file ke entitas UploadFiles
            UploadFiles uploadFile = new UploadFiles();
            uploadFile.setFileName(fileName);
            uploadFile.setFilePath(filePath.toString());
            uploadFile.setUploadedBy(teamName);
            uploadFile.setUploadedAt(LocalDateTime.now());
            uploadFile.setApprovalStatus("Pending");
            uploadFile.setEventStages(eventStages);
            uploadFile.setRegistration(registration);

            uploadFileRepository.save(uploadFile);
        } catch (IOException ex) {
            // Menangani eksepsi dan melempar pesan yang lebih spesifik
            throw new IOException("Failed to upload file: " + ex);
        }
    }
    }
