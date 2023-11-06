package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Fournisseurs;
import com.example.EASYSHOPAPI.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FournisseurServiceImp implements FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    //Méthode pour créer un fournisseur
    @Override
    public ResponseEntity<String> addFour(Fournisseurs fournisseurs) {
        fournisseurRepository.save(fournisseurs);
         return new ResponseEntity<>("Compte creer avec succés", HttpStatus.CREATED);
    }

    //Méthode pour afficher la liste des utilisateurs
    @Override
    public ResponseEntity<List<Fournisseurs>> getAllFournisseurs() {

        try {
            return new ResponseEntity<>(fournisseurRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    //Méthode pour vérifier si l'email existe déjà
    public boolean emailExisteDeja (String email){
        return fournisseurRepository.existsByEmail(email);
    }

}
