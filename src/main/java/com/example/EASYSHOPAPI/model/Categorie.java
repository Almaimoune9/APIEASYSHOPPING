package com.example.EASYSHOPAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String image;

    @ManyToMany(mappedBy = "categories")
    private Set<Fournisseurs> fournisseurs = new HashSet<>();

}
