package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Fournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseurs, Long> {

    Fournisseurs findFournisseursByEmail(String email);
    boolean existsByEmail(String email);
}
