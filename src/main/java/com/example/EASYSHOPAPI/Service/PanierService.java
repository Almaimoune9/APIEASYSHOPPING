package com.example.EASYSHOPAPI.Service;
import java.util.List;
import java.util.Optional;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.model.Panier;
import org.springframework.http.ResponseEntity;

public interface PanierService {

    ResponseEntity<Integer> createPanier(Panier panier);

    Optional<Panier> findById(Integer id);

    ResponseEntity<List<Panier>> listePanier();

    String delete(Integer panierId);
}
