package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.ClientServiceImp;
import com.example.EASYSHOPAPI.model.Client;
import com.example.EASYSHOPAPI.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://10.0.2.2:8080")
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImp clientServiceImp;

    //Créer un client
    @PostMapping("create")
    public ResponseEntity<Client> createClient(@RequestParam("client") String clientString,
                                               @RequestParam (value = "image", required = false)MultipartFile imageFile)
        throws Exception{
        Client client = new Client();
        try {
            client = new JsonMapper().readValue(clientString, Client.class);
        } catch (JsonProcessingException e){
            throw new Exception(e.getMessage());
        }
        Client savedClient = clientServiceImp.createClient(client, imageFile);
        return  new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    //Methode pour modifier le compte d'un client
    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,
                                               @Validated @RequestParam("client") String clientString,
                                               @RequestParam(value = "image", required = false) MultipartFile imageFile){
        Client client = new Client();
        try {
            client = new JsonMapper().readValue(clientString, Client.class);
        } catch (JsonProcessingException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Client clientUpdate = clientServiceImp.updateClient(id, client, imageFile);
            return new ResponseEntity<>(clientUpdate, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    //Liste des clients
    @GetMapping("liste")
    public ResponseEntity <List<Client>> listeClient(){
        return clientServiceImp.getAllClients();
    }


    //Afficher un client par son id
    @GetMapping("client/{id}")
    public Optional<Client> getClientById(@PathVariable Long id){
        return clientRepository.findClientsById(id);
    }

    @DeleteMapping("Delete/{id}")
    public String delete(@PathVariable Long id){
        return clientServiceImp.delete(id);
    }
}
