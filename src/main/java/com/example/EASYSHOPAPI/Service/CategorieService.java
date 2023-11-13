package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategorieService {

    //Créer une categorie
    Categorie createCategorie(Categorie categorie, MultipartFile imageFile) throws Exception;


    Categorie updatecategorie(Long id, Categorie categorie, MultipartFile imageFile) throws Exception;

    //Afficher la liste des categories
    ResponseEntity<List<Categorie>> listeCategorie();

    //Effacer une categorie
    String delete(Long id);
}
