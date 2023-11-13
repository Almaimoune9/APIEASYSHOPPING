package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Handlers.ResponseHandler;
import com.example.EASYSHOPAPI.Service.CategorieService;
import com.example.EASYSHOPAPI.Service.CategorieServiceImp;
import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.repository.CategorieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categoie")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private CategorieServiceImp categorieServiceImp;

    @Autowired
    private CategorieService categorieService;

    //Créer une categorie
    @PostMapping("create")
    @Operation(summary = "Créer une categorie")
    public ResponseEntity<Categorie> createCategorie(@RequestParam ("categorie") String categorieString,
                                                     @RequestParam(value = "image", required = false)MultipartFile imageFile)
        throws  Exception{
        Categorie categorie = new Categorie();

        try {
            categorie = new JsonMapper().readValue(categorieString, Categorie.class);
        } catch (JsonProcessingException e){
            throw  new Exception(e.getMessage());
        }
        Categorie createCategorie = categorieServiceImp.createCategorie(categorie, imageFile);

        return  new ResponseEntity<>(createCategorie, HttpStatus.CREATED);
    }
    @GetMapping("/liste")
    @Operation(summary = "Afficher la liste des categorie")
    public ResponseEntity<List<Categorie>> listeCategorie(){
        return categorieServiceImp.listeCategorie();
    }

    @GetMapping("cat/{id}")
    @Operation(summary = "Lire une catégorie")
    public Optional<Categorie> getCatById(@PathVariable Long id){
        return categorieRepository.findCategorieById(id);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimer une categorie")
    public String delete(@PathVariable Long id){
        return categorieServiceImp.delete(id);
    }
}
