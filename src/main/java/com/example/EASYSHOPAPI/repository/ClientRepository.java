package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientsByEmail(String email);
    boolean existsByEmail(String email);
}
