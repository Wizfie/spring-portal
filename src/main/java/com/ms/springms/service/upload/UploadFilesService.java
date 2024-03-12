package com.ms.springms.service.upload;

import com.ms.springms.entity.EventStages;
import com.ms.springms.entity.Registration;
import com.ms.springms.entity.UploadFiles;
import com.ms.springms.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UploadFilesService {

    @Autowired
    private UploadFileRepository uploadFileRepository;


    public void uploadFile(MultipartFile file ,String eventName , String teamName ,EventStages eventStages, Registration registration) throws IOException {


        // check file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            String fileName = file.getOriginalFilename();
            String uniqueFileName = generateUniqueFileName(fileName, eventName , teamName);
            String uploadDir = "./uploads/" + eventName + "/" + teamName + "/";
            Path uploadPath = Paths.get(uploadDir);

            // create folder
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath);

            // save file attribute
            UploadFiles uploadFile = new UploadFiles();
            uploadFile.setFileName(uniqueFileName);
            uploadFile.setFilePath(filePath.toString());
            uploadFile.setUploadedBy(teamName);
            uploadFile.setUploadedAt(LocalDateTime.now());
            uploadFile.setApprovalStatus("WAITING");
            uploadFile.setEventStages(eventStages);
            uploadFile.setRegistration(registration);

            uploadFileRepository.save(uploadFile);
        } catch (IOException ex) {
            throw new IOException("Failed to upload file: " + ex);
        }


    }

    public List<UploadFiles> getALl(){
        return uploadFileRepository.findAll();
    }

    private String generateUniqueFileName(String originalFileName, String eventName, String teamName) {
        String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String uniqueFileName = fileNameWithoutExtension + "_" + eventName + "_" + teamName + "_" + formattedDateTime + fileExtension;
        // Memastikan nama file unik dengan mengganti karakter yang tidak valid
        uniqueFileName = uniqueFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        return uniqueFileName;
    }
    }
