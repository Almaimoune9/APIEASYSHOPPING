package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Client;
import com.example.EASYSHOPAPI.model.Fournisseurs;
import com.example.EASYSHOPAPI.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FournisseurServiceImp implements FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Override
    public Fournisseurs createFournisseurs(Fournisseurs fournisseurs, MultipartFile imageFile) throws Exception {
        try {
            fournisseurs.setStatut("en attente");

            // Traitement de l'image s'il est fourni
            if (imageFile != null) {
                String imageLocation = "C:\\xampp\\htdocs\\easy_shopping";
                try {
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)) {
                        Files.createDirectories(imageRootLocation);
                    }
                    String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    fournisseurs.setImage("http://localhost/easy_shopping//images" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur de traitement de l'image : " + e.getMessage());
                }
            }

            return fournisseurRepository.save(fournisseurs);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la création du fournisseur : " + e.getMessage());
        }
    }

    @Override
    public List<Fournisseurs> getFournisseursEnAttente() {
        return fournisseurRepository.findByStatut("en attente");
    }

    @Override
    public ResponseEntity<List<Fournisseurs>> getFournisseursByStatut(String statut) {
        try {
            return new ResponseEntity<>(fournisseurRepository.findByStatut(statut), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> accepterFournisseur(long id) {
        try {
            Optional<Fournisseurs> optionalFournisseur = fournisseurRepository.findById(id);
            if (optionalFournisseur.isPresent()) {
                Fournisseurs fournisseur = optionalFournisseur.get();
                fournisseur.setStatut("accepté");
                fournisseurRepository.save(fournisseur);
                return new ResponseEntity<>("Fournisseur accepté avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Fournisseur non trouvé", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors de l'acceptation du fournisseur : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

    @Override
    public Fournisseurs updateFournisseurs(Long id, Fournisseurs fournisseurs, MultipartFile imageFile) throws Exception {
        try {
            Fournisseurs fournisseurExiste = fournisseurRepository.findFournisseursById(id)
                    .orElseThrow(() -> new NoSuchElementException("Client non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            fournisseurExiste.setNom(fournisseurs.getNom());
            fournisseurExiste.setEmail(fournisseurs.getEmail());
            fournisseurExiste.setAdresse(fournisseurs.getAdresse());
            // utilisateurExistant.setMotDePasse(utilisateur.getMotDePasse());


            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                // String emplacementImage = "C:\\xampp\\htdocs\\solution_express";
                String emplacementImage = "C:\\xampp\\htdocs\\easy_shopping";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                // utilisateurExistant.setImage("http://localhost/solution_express\\images" + nomImage);
                fournisseurs.setImage("http://localhost/easy_shopping//images" + nomImage);
            }

            // Enregistrer le user mise à jour
            return fournisseurRepository.save(fournisseurExiste);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour du fournisseur avec l'ID : " + id);
        }
    }

    @Override
    public String delete(Long id) {
        fournisseurRepository.deleteById(id);
        return "Fournisseurs supprimé";
    }

    //Méthode pour vérifier si l'email existe déjà
    public boolean emailExisteDeja (String email){
        return fournisseurRepository.existsByEmail(email);
    }

}
