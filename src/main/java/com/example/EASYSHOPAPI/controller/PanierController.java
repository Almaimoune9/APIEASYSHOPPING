package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.PanierServiceImp;
import com.example.EASYSHOPAPI.model.Panier;
import com.example.EASYSHOPAPI.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/panier")
public class PanierController {

    @Autowired
    private PanierServiceImp panierServiceImp;

    @PostMapping("/create")
    public ResponseEntity<Panier> createPanier(@RequestBody Panier panier) {
        Panier createdPanier = panierServiceImp.createPanier(panier);
        return new ResponseEntity<>(createdPanier, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/{panierId}/ajouterproduit")
    public ResponseEntity<Produit> ajouterProduitAuPanier(
            @PathVariable Long panierId,
            @RequestBody Produit produit,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            Produit addedProduit = panierServiceImp.ajouterProduitAuPanier(panierId, produit, imageFile);
            return new ResponseEntity<>(addedProduit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
