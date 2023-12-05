package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.EmailDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender  javaMailSender;

    @Value("mohamedelmoctaralmaimoune@gmail.com")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetail details) {
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getEmail());
            mailMessage.setText(details.getMessage());
            mailMessage.setSubject(details.getSujet());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Email envoyer avec succès...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Erreur lors de l'envoi de l'email ";
        }
    }
}
