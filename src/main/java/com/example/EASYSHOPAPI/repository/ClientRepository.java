package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientsById(Long id);
    Client findClientsByEmail(String email);
}
