package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Client;
import com.example.EASYSHOPAPI.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ClientServiceImp implements ClientService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client createClient(Client client, MultipartFile imageFile) throws Exception{
        if(clientRepository.findClientsByEmail(client.getEmail()) == null){
            //Traitement du fichier image
            if(imageFile != null){
                String imageLocation = "C:\\xampp\\htdocs\\easy_shopping";
                try {
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)){
                        Files.createDirectories(imageRootLocation);
                    }
                    String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    client.setImage("http://localhost/easy_shopping//images" + imageName);
                } catch (IOException e){
                    throw new Exception("Erreur de traitement de l'image:" + e.getMessage());
                }
            }
            return clientRepository.save(client);
        } else {
            throw  new IllegalArgumentException("Cet email" +client.getEmail() + "existe déjà");
        }

    }
    //Methode pour modifier un client
    @Override
    public Client updateClient(Long id, Client client, MultipartFile imageFile) throws Exception {
        try {
            Client clientExiste = clientRepository.findClientsById(id)
                    .orElseThrow(() -> new NoSuchElementException("Client non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            clientExiste.setNom(client.getNom());
            clientExiste.setEmail(client.getEmail());
            clientExiste.setAdresse(client.getAdresse());
            // utilisateurExistant.setMotDePasse(utilisateur.getMotDePasse());


            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                // String emplacementImage = "C:\\xampp\\htdocs\\solution_express";
                String emplacementImage = "C:\\xampp\\htdocs\\easy_shopping";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                // utilisateurExistant.setImage("http://localhost/solution_express\\images" + nomImage);
                clientExiste.setImage("http://localhost/easy_shopping//images" + nomImage);
            }

            // Enregistrer le user mise à jour
            return clientRepository.save(clientExiste);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour du client avec l'ID : " + id);

        }
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

    @Override
    public String delete(Long id) {
        clientRepository.deleteById(id);
        return "Client supprimé";
    }

}
