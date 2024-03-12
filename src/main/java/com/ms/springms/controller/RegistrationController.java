package com.ms.springms.controller;

import com.ms.springms.Exceptions.ResourceNotFoundException;
import com.ms.springms.model.registration.RegistrationRequest;
import com.ms.springms.model.registration.RegistrationResponseDTO;
import com.ms.springms.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/get-all")
    public ResponseEntity<List<RegistrationResponseDTO>> getAllRegistrations() {
        List<RegistrationResponseDTO> registrations = registrationService.getAllRegistrations();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            registrationService.registration(registrationRequest);
            return ResponseEntity.ok("Registration successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<RegistrationResponseDTO> getRegistrationById(@PathVariable Long registrationId) {
        try {
            RegistrationResponseDTO registrationResponseDTO = registrationService.getRegistrationById(registrationId);
            return ResponseEntity.ok(registrationResponseDTO);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi kesalahan saat mengambil registrasi dengan ID " + registrationId, ex);
        }
    }

}
