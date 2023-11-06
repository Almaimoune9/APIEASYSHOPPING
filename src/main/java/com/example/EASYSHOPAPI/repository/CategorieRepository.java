package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    boolean existsByNom(String nom);
}
