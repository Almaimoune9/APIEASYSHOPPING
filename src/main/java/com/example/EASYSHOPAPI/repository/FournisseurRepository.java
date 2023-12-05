package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Fournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseurs, Long> {


    Optional<Fournisseurs> findFournisseursById(Long id);

    Fournisseurs findFournisseursByEmail(String email);
    boolean existsByEmail(String email);


}
