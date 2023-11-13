package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Optional<Produit> findProduitById(Long id);

    Optional<Produit> findProduitByNom(String nom);
}
