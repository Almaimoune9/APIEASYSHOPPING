package com.example.EASYSHOPAPI.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class EmailDetail {

    private String email;

    private String message;

    private String sujet;
    public EmailDetail(String email, String sujet, String message) {
        this.email = email;
        this.sujet = sujet;
        this.message = message;
    }

}
