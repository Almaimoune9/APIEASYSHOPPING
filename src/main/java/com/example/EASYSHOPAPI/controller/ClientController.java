package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.ClientServiceImp;
import com.example.EASYSHOPAPI.model.Client;
import com.example.EASYSHOPAPI.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImp clientServiceImp;

    @PostMapping("addClient")
    public ResponseEntity<String> addClient(@RequestBody Client client){
        String email = client.getEmail();
        if (clientServiceImp.emailExistDeja(email)){
            return new ResponseEntity<>("Cet email existe déjà", HttpStatus.UNAUTHORIZED);
        }else
            return clientServiceImp.addClient(client);
    }

    //Méthode pour la connexion
    @PostMapping("connexion")
    public ResponseEntity<String> connexion(@RequestBody Client client){
        String email = client.getEmail();
        String motdepass = client.getMotdepass();
        //Recherche du client par son mail
        Client e = clientRepository.findClientsByEmail(email);

        //Verifier si le compte existe et si la mot de pass est correct
        if (e != null && e.getMotdepass().equals(motdepass)){
            return new ResponseEntity<>("Connexion reussie"+ e.getNom().toUpperCase(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email ou mot de pass incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("ListClient")
    public ResponseEntity <List<Client>> listeClient(){
        return clientServiceImp.getAllClients();
    }
}
