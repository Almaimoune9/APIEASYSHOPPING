package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Fournisseurs;
import com.example.EASYSHOPAPI.repository.FournisseurRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FournisseurService {

    //créer fournisseur
    ResponseEntity<String> addFour(Fournisseurs fournisseurs);

    //ListeFournisseurs
    ResponseEntity<List <Fournisseurs>> getAllFournisseurs();
}
