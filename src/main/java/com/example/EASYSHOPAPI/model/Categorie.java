package com.example.EASYSHOPAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "categorie" ,cascade = CascadeType.ALL)
    private List<Fournisseurs> fournisseurs;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Panier> paniers;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Notification> notifications;

}
