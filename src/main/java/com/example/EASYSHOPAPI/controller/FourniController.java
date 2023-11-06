package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.FournisseurServiceImp;
import com.example.EASYSHOPAPI.model.Fournisseurs;
import com.example.EASYSHOPAPI.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class FourniController {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurServiceImp fournisseurServiceImp;

    public FourniController(FournisseurRepository fournisseurRepository){
        this.fournisseurRepository= fournisseurRepository;
    }

    @PostMapping("insFour")
    public ResponseEntity<String> addFourni(@RequestBody Fournisseurs fournisseurs){
        String email = fournisseurs.getEmail();
        if (fournisseurServiceImp.emailExisteDeja(email)){
            return new ResponseEntity<>("Cet email existe déjà", HttpStatus.UNAUTHORIZED);
        }
        return fournisseurServiceImp.addFour(fournisseurs);
    }

    //Méthode pour la connexion
    @PostMapping("connexion")
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

    @GetMapping("listefournisseur")
    public ResponseEntity<List<Fournisseurs>> listeFournisseur(){
        return fournisseurServiceImp.getAllFournisseurs();
    }
}
