package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Panier;
import com.example.EASYSHOPAPI.model.Produit;
import com.example.EASYSHOPAPI.repository.PanierRepository;
import com.example.EASYSHOPAPI.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ProduitServiceImp implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private  PanierRepository panierRepository;


    @Override
    public Produit createProduit(Produit produit, MultipartFile imageFile) throws Exception {
        //Verifier si le produit existe
        Panier panier = panierRepository.findById(produit.getPanier().getPanierId()).orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec l'id " + produit.getPanier().getPanierId()));
        //Verifie si le produit existe dans le panier
        Optional<Produit> produitExist = produitRepository.findByNomAndPanier(produit.getNom(), panier);
        if (produitExist.isPresent()) {
            throw new Exception("Le produit " + produit.getNom() + "existe dans ce panier" + panier.getTitre());
        }

        //Optional<Produit> existingProduit = produitRepository.findProduitByNom(produit.getNom());

        //if (existingProduit.isEmpty()) {
        // if (imageFile != null) {
        //if (produitExist != null) {
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


        return produitRepository.save(produit);
    }
      //  } else {
        //    throw new IllegalArgumentException("Ce produit " + produit.getNom() + "existe deja");
       // }
   // }

    @Override
    public Produit updateProduit(Long id, Produit produit, MultipartFile imageFile) throws Exception {
        try{
            Produit produitExiste = produitRepository.findProduitById(id)
                    .orElseThrow(() -> new NoSuchElementException("Produit non trouvé avec l'ID:" + id));
            produitExiste.setNom(produit.getNom());
            produitExiste.setDescription(produit.getDescription());
            produitExiste.setQte(produit.getQte());

            if (imageFile != null){
                String emplacementImage = "C:\\xampp\\htdocs\\easy_shopping";

                String nomImage = UUID.randomUUID().toString()+"_"+ imageFile.getOriginalFilename();

                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);

                produitExiste.setImage("http://localhost/easy_shopping//images" + nomImage);
            }
            return produitRepository.save(produitExiste);
        }catch (NoSuchElementException ex){
            throw  new NoSuchElementException("Une erreur s'est produite" + id);
        }
    }

    @Override
    public ResponseEntity<List<Produit>> getAllProduit() {
        try {
            return new ResponseEntity<>(produitRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public String delete(Long id) {
        produitRepository.deleteById(id);
        return "Produit supprimé";
    }
}


