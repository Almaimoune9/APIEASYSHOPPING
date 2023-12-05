package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.repository.CategorieRepository;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CategorieServiceImp implements CategorieService{

    @Autowired
    private CategorieRepository categorieRepository;

    //Créer une categorie
    @Override
    public Categorie createCategorie(Categorie categorie, MultipartFile imageFile) throws Exception {
        if (categorieRepository.findCategorieByNom(categorie.getNom()) == null){
            //traitement de l'image
            if (imageFile != null){
                String imageLocation = "C:\\xampp\\htdocs\\easy_shopping";
                try {
                    //Créer s'il n'existe pas
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)){
                        Files.createDirectories(imageRootLocation);
                    }

                    //Donne un nom unique pour l'image
                    String imageName = UUID.randomUUID().toString()+"_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    categorie.setImage("http://localhost/easy_shopping/images/" + imageName);
                } catch (IOException e){
                    throw new Exception("Erreur de traitement de l'image" + e.getMessage());
                }
            }
            //Ajouter la catgorie s'il n'existe pas
            return categorieRepository.save(categorie);
        }else {
            throw new IllegalArgumentException("Cette categorie " + categorie.getNom() + "existe déjà" );
        }
    }

    @Override
    public Categorie updatecategorie(Long id, Categorie categorie, MultipartFile imageFile) throws Exception {
        try {
            Categorie categorieExiste = categorieRepository.findCategorieById(id);
            if (categorieExiste == null)
                throw new NoSuchElementException("Ctégorie non trouvé avec l'id: " +id);
            categorieExiste.setNom(categorie.getNom());

            if (imageFile != null){
                String emplacemeImage = "C:\\xampp\\htdocs\\easy_shopping";

                String nomImage = UUID.randomUUID().toString()+ "_" + imageFile.getOriginalFilename();

                Path cheminImage = Paths.get(emplacemeImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);

                categorieExiste.setImage("http://localhost/easy_shopping//images" + nomImage);
            }
            return categorieRepository.save(categorieExiste);
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("Une erreur s'est produite dans la mise à jour de la categorie: " + id);
        }
    }


    @Override
    public ResponseEntity<List<Categorie>> listeCategorie() {
        return new ResponseEntity<>(categorieRepository.findAll(), HttpStatus.OK);
    }


    @Override
    public String delete(Long id) {
        categorieRepository.deleteById(id);
        return "Catégorie supprimée";
    }

}
