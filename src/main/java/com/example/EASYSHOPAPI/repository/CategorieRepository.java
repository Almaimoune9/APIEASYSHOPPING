package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {


    Optional<Categorie> findCategorieById(Long id);

    Client findCategorieByNom(String nom);

}
