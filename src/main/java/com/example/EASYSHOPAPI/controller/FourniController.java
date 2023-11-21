package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.FournisseurServiceImp;
import com.example.EASYSHOPAPI.model.Fournisseurs;
import com.example.EASYSHOPAPI.repository.FournisseurRepository;
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
@RequestMapping("api/fournisseur")
public class FourniController {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurServiceImp fournisseurServiceImp;

    public FourniController(FournisseurRepository fournisseurRepository){
        this.fournisseurRepository= fournisseurRepository;
    }

    @PostMapping("create")
    @Operation(summary = "Créer un fournissuer")
    public ResponseEntity<String> createFournisseur(@RequestParam ("fournisseur") String fournisseurString,
                                                    @RequestParam(value = "image", required = false) MultipartFile imageFile)
        throws Exception{
        Fournisseurs fournisseurs = new Fournisseurs();
        try {
            fournisseurs = new JsonMapper().readValue(fournisseurString, Fournisseurs.class);
        } catch (JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        Fournisseurs savedFournisseur = fournisseurServiceImp.createFournisseurs(fournisseurs, imageFile);
        String responseMessage = "Fournisseurs créer avec succés, en atente d'approbation";
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    //Méthode pour la connexion
    @PostMapping("connexion")
    @Operation(summary = "La connexion d'un founisseur existant")
    public ResponseEntity<String> connexion(@RequestBody Fournisseurs fournisseurs){
        String email = fournisseurs.getEmail();
        String motdepass = fournisseurs.getMotdepass();

        //Rechercher le fournisseur par le mail
        Fournisseurs u = fournisseurRepository.findFournisseursByEmail(email);

        //vérifier si le compte existe et si le mot de pass est correcte
        if (u != null && u.getMotdepass().equals(motdepass)){
            return new ResponseEntity<>("Connéxion reussie " + u.getNom().toUpperCase(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Email ou mot de pass incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("liste")
    @Operation(summary = "Afficher la liste des fournisseurs")
    public ResponseEntity<List<Fournisseurs>> listeFournisseur(){
        return fournisseurServiceImp.getAllFournisseurs();
    }

    @GetMapping("fournisseur/{id}")
    @Operation(summary = "Lire un fournisseur")
    public Optional<Fournisseurs> getFourniById(@PathVariable Long id){
        return fournisseurRepository.findFournisseursById(id);
    }
    @GetMapping("/fournisseurs/enAttente")
    public ResponseEntity<List<Fournisseurs>> getFournisseursEnAttente() {
        List<Fournisseurs> fournisseursEnAttente = fournisseurServiceImp.getFournisseursEnAttente();
        return new ResponseEntity<>(fournisseursEnAttente, HttpStatus.OK);
    }

    @PutMapping("/{id}/accepter")
    @Operation(summary = "Approuver un fournisseur ")
    public ResponseEntity<String> accepterFournisseur(@PathVariable long id) {
        return fournisseurServiceImp.accepterFournisseur(id);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Modifier un fournisseur")
    public ResponseEntity<Fournisseurs> updateFournisseur(@PathVariable Long id,
                                               @Validated @RequestParam("fournisseur") String fournisseurString,
                                               @RequestParam(value = "image", required = false) MultipartFile imageFile){
        Fournisseurs fournisseurs = new Fournisseurs();
        try {
            fournisseurs = new JsonMapper().readValue(fournisseurString, Fournisseurs.class);
        } catch (JsonProcessingException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Fournisseurs fournisseurUpdate = fournisseurServiceImp.updateFournisseurs(id, fournisseurs, imageFile);
            return new ResponseEntity<>(fournisseurUpdate, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimé un fournisseur par son id")
    public String delete(@PathVariable Long id){
        return fournisseurServiceImp.delete(id);
    }

}
