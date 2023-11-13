package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientService {


    Client createClient(Client client, MultipartFile imageFile) throws Exception;


    //Methode pour modifier un client
    Client updateClient(Long id, Client client, MultipartFile imageFile) throws Exception;

    //La liste des clients
    ResponseEntity<List<Client>> getAllClients();

    String delete(Long id);
}
