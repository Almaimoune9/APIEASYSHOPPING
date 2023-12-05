package com.example.EASYSHOPAPI.controller;

import com.example.EASYSHOPAPI.Service.ClientServiceImp;
import com.example.EASYSHOPAPI.model.Client;
import com.example.EASYSHOPAPI.model.Response;
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
import java.util.Map;
import java.util.Optional;


@RestController
//@CrossOrigin(origins = "http://10.175.48.9:8080")
@RequestMapping("/api/client/")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientServiceImp clientServiceImp;

    //Créer un client
    @CrossOrigin
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
    @PostMapping("/connexion")
    public ResponseEntity<Response> connexion(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String motdepass = requestData.get("motdepass");

        // Recherche du client par son mail
        Client e = clientRepository.findClientsByEmail(email);

        // Vérifier si le compte existe et si le mot de passe est correct
        if (e != null && e.getMotdepass().equals(motdepass)) {
            String successMessage = "Connexion réussie " + e.getNom().toUpperCase();
            Response successResponse = new Response(successMessage);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else {
            String errorMessage = "Email ou mot de passe incorrect";
            Response errorResponse = new Response(errorMessage);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    //Liste des clients
    @GetMapping("/liste")
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
