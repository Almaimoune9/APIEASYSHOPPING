package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Panier;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Integer> {



    Panier getByTitre(String titre);
    Optional<Panier> getPanierByPanierId(Integer id);

}
