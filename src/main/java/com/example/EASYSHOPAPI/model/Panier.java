package com.example.EASYSHOPAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer panierId;

    @Column(nullable = false)
    private String titre;


    @Column(nullable = false)
    private String dateLivraison;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Produit> produit;

    //@JsonIgnoreProperties(value = {"categorie_id"})
    @ManyToOne
    private Categorie categorie;
}
