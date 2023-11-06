package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.CategorieService;
import com.example.EASYSHOPAPI.Service.CategorieServiceImp;
import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private CategorieServiceImp categorieServiceImp;

    @Autowired
    private CategorieService categorieService;

    @PostMapping("/addCat")
    public ResponseEntity<String> addCat(@RequestBody Categorie categorie){
        String nom = categorie.getNom();
        if (categorieRepository.existsByNom(nom)){
            return new ResponseEntity<>("Categorie existe déjà", HttpStatus.CREATED);
        } else {
            return categorieServiceImp.addCat(categorie);
        }
    }

    @GetMapping("/listeCategorie")
    public ResponseEntity<List<Categorie>> listeCategorie(){
        return categorieServiceImp.listeCategorie();
    }

    @PutMapping("/updateCat/{id}")
    public Categorie update(@PathVariable long id, @RequestBody Categorie categorie){
        return categorieServiceImp.modifier(id, categorie);
    }

    @DeleteMapping("/deleteCat/{id}")
    public String delete(@PathVariable Long id){
        return categorieServiceImp.delete(id);
    }
}
