package com.example.EASYSHOPAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String motdepass;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String image;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Commentaire> commentaire;
}
