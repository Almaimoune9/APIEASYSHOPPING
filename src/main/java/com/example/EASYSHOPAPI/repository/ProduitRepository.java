package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Panier;
import com.example.EASYSHOPAPI.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Optional<Produit> findProduitById(Long id);

    Optional<Produit> findProduitByNom(String nom);

    Optional<Produit> findByNomAndPanier(String nom, Panier panierId);

    void deleteByPanier_PanierId(Integer panierId);


}
