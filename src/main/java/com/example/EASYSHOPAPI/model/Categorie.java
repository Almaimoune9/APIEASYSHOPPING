package com.example.EASYSHOPAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "categorie")
    private List<Fournisseurs> fournisseurs = new ArrayList<>();

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    private Set<Panier> paniers;

}
