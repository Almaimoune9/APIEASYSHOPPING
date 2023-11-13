package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Fournisseurs;
import com.example.EASYSHOPAPI.repository.FournisseurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FournisseurService {

    Fournisseurs createFournisseurs(Fournisseurs fournisseurs, MultipartFile imageFile) throws Exception;

    List<Fournisseurs> getFournisseursEnAttente();

    ResponseEntity<List<Fournisseurs>> getFournisseursByStatut(String statut);

    ResponseEntity<String> accepterFournisseur(long id);

    //ListeFournisseurs
    ResponseEntity<List <Fournisseurs>> getAllFournisseurs();

    Fournisseurs updateFournisseurs(Long id,Fournisseurs fournisseurs, MultipartFile imageFile) throws Exception;

    String delete(Long id);
}
