package com.example.EASYSHOPAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Data
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;


    @Column(nullable = false)
    private Date dateLimite;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Produit> produit;

    @ManyToOne
    @JsonIgnoreProperties(value = {"categorie_id"})
    private Categorie categorie;
}
