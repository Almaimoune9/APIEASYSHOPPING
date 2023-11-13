package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Produit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProduitService {
    Produit createProduit(Produit produit, MultipartFile imageFile) throws Exception;


    Produit updateProduit(Long id, Produit produit, MultipartFile imageFile) throws Exception;

    ResponseEntity<List<Produit>>   getAllProduit();

    String delete(Long id);


}
