package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Client;
import com.example.EASYSHOPAPI.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImp implements ClientService{

    @Autowired
    private ClientRepository clientRepository;
    @Override
    public ResponseEntity<String> addClient(Client client) {
        clientRepository.save(client);
        return new ResponseEntity<>("Client créer avec succés", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    //Méthode pour vérifier si l'email existe déjà
    public boolean emailExistDeja(String email){
        return clientRepository.existsByEmail(email);
    }
}
