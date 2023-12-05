package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.*;
import com.example.EASYSHOPAPI.repository.CategorieRepository;
import com.example.EASYSHOPAPI.repository.PanierRepository;
import com.example.EASYSHOPAPI.repository.ProduitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PanierServiceImp implements PanierService{

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public ResponseEntity<Integer> createPanier(Panier panier) {
        try {
            if (panier != null && panier.getTitre() != null && !panier.getTitre().trim().isEmpty()) {
                Panier existingPanier = panierRepository.getByTitre(panier.getTitre());

                if (existingPanier == null) {
                    Panier panier1 = panierRepository.save(panier);
                    sendPanierCreationEmail(panier1);

                    // Retourner explicitement l'ID du panier
                    return new ResponseEntity<>(panier.getPanierId(), HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void sendPanierCreationEmail(Panier panier){
        Categorie categorie = categorieRepository.findCategorieById(panier.getCategorie().getId());
        List<Fournisseurs> fournisseurs = categorie.getFournisseurs();

        for (Fournisseurs fournisseur :fournisseurs){
            String emailAddress = fournisseur.getEmail();
            String sujet = "Nouveau panier créé : " + panier.getTitre();
            String message = "Un nouveau panier a été créé avec le titre : " + panier.getTitre() + ". La date limite de livraison est: "+ panier.getDateLivraison() + ". Vous pouvez faire des propositions";

            emailService.sendSimpleMail(new EmailDetail(emailAddress, sujet, message));
        }
    }




    public Produit ajouterProduitAuPanier(Integer panierId, String produitJson, MultipartFile imageFile) throws Exception {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier introuvable avec ID : " + panierId));

        ObjectMapper objectMapper = new ObjectMapper();
        Produit produit = objectMapper.readValue(produitJson, Produit.class);

        try {

            if (produitRepository.findProduitByNom(produit.getNom()) == null) {
                return produitRepository.save(produit);
            } else {
                throw new IllegalArgumentException("Ce produit " + produit.getNom() + " existe déjà");
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'ajout du produit au panier : " + e.getMessage());
        }
    }

    @Override
    public Optional<Panier> findById(Integer id) {
        return panierRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<Panier>> listePanier() {
        return new ResponseEntity<>(panierRepository.findAll(), HttpStatus.OK);
    }

    //@Override
    //public String delete(Integer panierId) {
     //   panierRepository.deleteById(panierId);
      //  return "Panier supprimé avec succès";
   // }

    @Override
    @Transactional
    public String delete(Integer panierId) {
        // Supprimer les produits liés au panier
        produitRepository.deleteByPanier_PanierId(panierId);

        // Ensuite, supprimer le panier lui-même
        panierRepository.deleteById(panierId);

        return "Panier et produits associés supprimés avec succès";
    }
}
