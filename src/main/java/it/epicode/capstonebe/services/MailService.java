package it.epicode.capstonebe.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    public void sendEmail(String email, String emailSubject, String emailText){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(emailSubject);
        message.setText(emailText);
    }
}
