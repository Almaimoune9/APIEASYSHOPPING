package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.EmailDetail;

public interface EmailService {

    String sendSimpleMail(EmailDetail details);
}
