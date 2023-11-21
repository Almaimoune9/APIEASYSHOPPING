package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.model.Panier;
import com.example.EASYSHOPAPI.model.Produit;
import com.example.EASYSHOPAPI.repository.PanierRepository;
import com.example.EASYSHOPAPI.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class PanierServiceImp implements PanierService{

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private PanierRepository panierRepository;


    public String createPanier(Panier panier) {
        try {
            if (panier != null && panier.getTitre() != null && !panier.getTitre().trim().isEmpty()) {
                Panier existingPanier = panierRepository.getByTitre(panier.getTitre());
    
                if (existingPanier == null) {
                    panierRepository.save(panier);
                    return "Panier créé avec succès";
                } else {
                    return "Panier existe déjà!";
                }
            } else {
                return "Le nom ne doit pas être vide";
            }
        } catch (RuntimeException e) {
            return "Erreur lors de la création du panier: " + e.getMessage();
        }
    }

    public Produit ajouterProduitAuPanier(Long panierId, Produit produit, MultipartFile imageFile) throws Exception {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier introuvable avec ID : " + panierId));

        if (produitRepository.findByNom(produit.getNom()) == null) {
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

                    produit.setImage("http://localhost/easy_shopping/images/" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement de l'image: " + e.getMessage());
                }
            }

            produit.setPanier(panier);
            return produitRepository.save(produit);
        } else {
            throw new IllegalArgumentException("Ce produit " + produit.getNom() + " existe déjà");
        }
    }

    @Override
    public Optional<Panier> findById(Long id) {
        return panierRepository.findById(id);
    }
}
