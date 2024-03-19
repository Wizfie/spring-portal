package com.ms.springms.service.upload;

import com.ms.springms.entity.EventStages;
import com.ms.springms.entity.Registration;
import com.ms.springms.entity.UploadFiles;
import com.ms.springms.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UploadFilesService {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    private final Path fileStorageLocation;

    private final String uploadDir = "./uploads/" ;


    public UploadFilesService() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to create directory for uploaded files", ex);
        }
    }




    public void uploadFile(MultipartFile file ,String eventName , String teamName ,EventStages eventStages, Registration registration) throws IOException {


        // check file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String uniqueFileName = generateUniqueFileName(fileName, eventName , teamName);
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

    public void updateFileById(Long fileId, MultipartFile file, String eventName, String teamName, EventStages eventStages, Registration registration) throws IOException {
        // check file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            // Retrieve file by ID
            Optional<UploadFiles> optionalUploadFile = uploadFileRepository.findById(fileId);
            if (optionalUploadFile.isPresent()) {
                UploadFiles uploadFile = optionalUploadFile.get();

                // Generate new unique file name
                String fileName = file.getOriginalFilename();
                String uniqueFileName = generateUniqueFileName(fileName, eventName, teamName);
                Path uploadPath = Paths.get(uploadDir);

                // Create folder if not exists
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(uniqueFileName);

                // Check if file with same name already exists
                if (Files.exists(filePath)) {
                    // Delete existing file
                    Files.delete(filePath);
                }

                // Delete old file from local filesystem
                Path oldFilePath = Paths.get(uploadFile.getFilePath());
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath);
                }
                Files.copy(file.getInputStream(), filePath);

                // Update file attributes
                uploadFile.setFileName(uniqueFileName);
                uploadFile.setFilePath(filePath.toString());
                uploadFile.setUploadedBy(teamName);
                uploadFile.setUploadedAt(LocalDateTime.now());
                uploadFile.setApprovalStatus("WAITING");
                uploadFile.setEventStages(eventStages);
                uploadFile.setRegistration(registration);


                uploadFileRepository.save(uploadFile);
            } else {
                throw new IllegalArgumentException("File not found with ID: " + fileId);
            }
        } catch (IOException ex) {
            throw new IOException("Failed to upload file: " + ex);
        }
    }


    public void approveFile(Long fileId, String approvalStatus) {
        Optional<UploadFiles> optionalFile = uploadFileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            UploadFiles uploadFile = optionalFile.get();
            uploadFile.setApprovalStatus(approvalStatus);
            uploadFileRepository.save(uploadFile);
        } else {
            throw new RuntimeException("File not found with id: " + fileId);
        }
    }

    public void rejectFile(Long fileId, String description, MultipartFile newFile) throws IOException {
        Optional<UploadFiles> optionalFile = uploadFileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            UploadFiles uploadFile = optionalFile.get();
            uploadFile.setApprovalStatus("REJECT");
            uploadFile.setDescription(description);
            if (newFile != null) {
                // Jika file baru diunggah, update file yang ada
                uploadFile.setFileName(newFile.getOriginalFilename());
                uploadFile.setFilePath(saveFile(newFile));
            }
            uploadFileRepository.save(uploadFile);
        } else {
            throw new RuntimeException("File not found with id: " + fileId);
        }
    }
    public List<UploadFiles> getALl(){
        return uploadFileRepository.findAll();
    }


    public Resource downloadFile(String fileName) throws MalformedURLException {
        Path  filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()){
            return resource;
        } else {
                throw new RuntimeException("File no found " + fileName);
        }
    }


    private String saveFile(MultipartFile file) throws IOException {
        String uniqueFileName = generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = this.fileStorageLocation.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }

    private String generateUniqueFileName(String originalFileName, String... additionalParams) {
        String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        StringBuilder uniqueFileNameBuilder = new StringBuilder(fileNameWithoutExtension);

        // Menambahkan parameter tambahan jika ada
        for (String param : additionalParams) {
            uniqueFileNameBuilder.append("_").append(param);
        }

        uniqueFileNameBuilder.append(fileExtension);
        String uniqueFileName = uniqueFileNameBuilder.toString();

        // Memastikan nama file unik dengan mengganti karakter yang tidak valid
        uniqueFileName = uniqueFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        return uniqueFileName;
    }
    }
