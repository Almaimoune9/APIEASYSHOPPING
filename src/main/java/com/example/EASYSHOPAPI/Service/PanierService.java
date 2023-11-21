package com.example.EASYSHOPAPI.Service;
import java.util.Optional;

import com.example.EASYSHOPAPI.model.Panier;

public interface PanierService {

    String createPanier(Panier panier);

    Optional<Panier> findById(Long id);
}
