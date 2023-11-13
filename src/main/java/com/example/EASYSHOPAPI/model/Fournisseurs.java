package com.example.EASYSHOPAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Fournisseurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String adresse;

    @Column(nullable = false, length = 50)
    private String motdepass;

    @Column(nullable = false)
    private String image;

    @ManyToMany
    @JoinColumn(name = "categorie_id")
    private Set<Categorie> categories = new HashSet<>();

    @Column(nullable = false)
    private String statut;
}
