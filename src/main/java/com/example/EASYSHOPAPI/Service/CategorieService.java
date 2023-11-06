package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Categorie;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategorieService {
    //Créer une categorie
    ResponseEntity<String> addCat(Categorie categorie);
    //Afficher la liste des categories
    ResponseEntity<List<Categorie>> listeCategorie();
    //Modifier categorie
    Categorie modifier(Long id, Categorie categorie);
    //Effacer une categorie
    String delete(Long id);
}
