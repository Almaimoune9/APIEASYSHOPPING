package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.PanierServiceImp;
import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.model.Panier;
import com.example.EASYSHOPAPI.model.Produit;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/panier")
public class PanierController {

    @Autowired
    private PanierServiceImp panierServiceImp;

    @PostMapping("/create")
    public ResponseEntity<Integer> create(@RequestBody Panier panier){
        return panierServiceImp.createPanier(panier);
    }

    @CrossOrigin
    @PostMapping("/{panierId}/ajouterproduit")
    public ResponseEntity<Produit> ajouterProduitAuPanier(
            @PathVariable Integer panierId,
            @RequestParam("produit") String produitJson,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        System.out.println("panierId: " + panierId);
        System.out.println("produitJson: " + produitJson);
        System.out.println("imageFile: " + imageFile);

        try {
            Produit addedProduit = panierServiceImp.ajouterProduitAuPanier(panierId, produitJson, imageFile);
            return new ResponseEntity<>(addedProduit, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/liste")
    @Operation(summary = "Afficher la liste des paniers")
    public ResponseEntity<List<Panier>> listePanier(){
        return panierServiceImp.listePanier();
    }

    @DeleteMapping("/Delete/{panierId}")
    public ResponseEntity<String> delete(@PathVariable Integer panierId) {
        String message = panierServiceImp.delete(panierId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
