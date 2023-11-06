package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorieServiceImp implements CategorieService{

    @Autowired
    private CategorieRepository categorieRepository;
    @Override
    public ResponseEntity<String> addCat(Categorie categorie) {
        categorieRepository.save(categorie);
        return new ResponseEntity<>("Categorie Créer avec succés", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Categorie>> listeCategorie() {
        return new ResponseEntity<>(categorieRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public Categorie modifier(Long id, Categorie categorie) {
        return categorieRepository.findById(id)
                .map(cat -> {
                    cat.setNom(categorie.getNom());
                    return categorieRepository.save(cat);
                }).orElseThrow(() -> new RuntimeException(("Categorie non trouvée")));
    }

    @Override
    public String delete(Long id) {
        categorieRepository.deleteById(id);
        return "Catégorie supprimée";
    }

    public boolean categorieexiste(String nom){
        return categorieRepository.existsByNom(nom);
    }
}
