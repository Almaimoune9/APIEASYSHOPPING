package com.example.EASYSHOPAPI.controller;


import com.example.EASYSHOPAPI.Service.ProduitServiceImp;
import com.example.EASYSHOPAPI.model.Produit;
import com.example.EASYSHOPAPI.repository.ProduitRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/produit")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitServiceImp produitServiceImp;

    @PostMapping("/create")
    @Operation(summary = "Creation d'un produit")
    @CrossOrigin(origins = "http://10.0.2.2:8081")
    public ResponseEntity<Produit> createProduit(@RequestParam("produit") String produitString,
                                                 @RequestParam(value = "image", required = false)MultipartFile imageFile)
        throws Exception{
        Produit produit = new Produit();
        try {
            produit = new JsonMapper().readValue(produitString, Produit.class);
        }catch (JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        Produit saveProduit  = produitServiceImp.createProduit(produit, imageFile);

        return new  ResponseEntity<>(saveProduit, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Mise à jour d'un produit")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id,
                                               @Validated @RequestParam("produit") String produitString,
                                               @RequestParam(value = "image", required = false) MultipartFile imageFile){
        Produit produit = new Produit();
        try {
            produit = new JsonMapper().readValue(produitString, Produit.class);
        } catch (JsonProcessingException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Produit produitUpdate = produitServiceImp.updateProduit(id, produit, imageFile);
            return new ResponseEntity<>(produitUpdate, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("Liste")
    @Operation(summary = "Afficher la liste des produits")
    public ResponseEntity<List<Produit>> listeProduit(){
        return produitServiceImp.getAllProduit();
    }

    @GetMapping("produit/{id}")
    @Operation(summary = "Lire un produit spécifique")
    public Optional<Produit> getProduitById(@PathVariable Long id){
        return produitRepository.findProduitById(id);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimé un produit par son id")
    public String delete(@PathVariable Long id){
        return produitServiceImp.delete(id);
    }
}
