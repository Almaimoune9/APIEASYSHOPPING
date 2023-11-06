package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {
    //Ajouter un client
    ResponseEntity<String> addClient(Client client);

    //La liste des clients
    ResponseEntity<List<Client>> getAllClients();
}
