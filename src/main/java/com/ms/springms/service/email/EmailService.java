package com.ms.springms.service.email;

import com.ms.springms.model.email.MailRequest;
import com.ms.springms.model.email.MailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public MailResponse sendEmail(MailRequest request){
        MailResponse response = new MailResponse();
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setFrom(request.getFrom());
            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());


            String emailContent = "<html><body>"
                    + "<h3>Dear " + request.getName() + ",</h3>"
                    + "<p>" + request.getText() + "</p>"
                    + "<p>Best regards,</p><p>My name</p>"
                    + "</body></html>";
            helper.setText(emailContent, true);
            javaMailSender.send(message);

            response.setMessage("Mail send to : " + request.getTo());
            response.setStatus(true);
        } catch (MessagingException e) {
            response.setMessage("Mail Fail Send");
            response.setStatus(false);
        }
        return response;
    }


}