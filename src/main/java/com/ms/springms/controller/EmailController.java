package com.ms.springms.controller;

import com.ms.springms.model.email.MailRequest;
import com.ms.springms.model.email.MailResponse;
import com.ms.springms.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public MailResponse sendEmail(@RequestBody MailRequest request){
            return emailService.sendEmail(request);
    }
}
