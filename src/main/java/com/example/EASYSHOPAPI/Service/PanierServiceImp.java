package com.example.EASYSHOPAPI.Service;

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
import java.util.UUID;

@Service
public class PanierServiceImp implements PanierService{

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private PanierRepository panierRepository;

    public Panier createPanier(Panier panier){
        return panierRepository.save(panier);
    }

    public Produit ajouterProduitAuPanier(Long panierId, Produit produit, MultipartFile imageFile) throws Exception {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier introuvable avec ID : " + panierId));

        if (produitRepository.findProduitByNom(produit.getNom()) == null) {
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
}
