package com.example.EASYSHOPAPI.Service;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.model.Notification;
import com.example.EASYSHOPAPI.repository.CategorieRepository;
import com.example.EASYSHOPAPI.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsForCategory(Long categoryId) {
        return notificationRepository.findByCategorieId(categoryId);
    }

    public Notification createNotification(String message, Long categoryId) {
        Notification notification = new Notification();
        notification.setMessage(message);

        // Charger la catégorie depuis la base de données et l'associer à la notification
        Categorie categorie = loadCategoryById(categoryId);
        notification.setCategorie(categorie);

        return notificationRepository.save(notification);
    }

    // Méthode pour charger une catégorie depuis la base de données
    private Categorie loadCategoryById(Long categoryId) {
        // Implémentez cette méthode selon votre logique pour charger une catégorie
        // depuis la base de données. Vous pouvez utiliser le repository de catégorie ou
        // toute autre logique appropriée.
        // Exemple :
        // return categorieRepository.findById(categoryId).orElse(null);
        return categorieRepository.findById(categoryId).orElse(null);
    }
}

