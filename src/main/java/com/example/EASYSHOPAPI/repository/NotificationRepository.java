package com.example.EASYSHOPAPI.repository;

import com.example.EASYSHOPAPI.model.Categorie;
import com.example.EASYSHOPAPI.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByCategorieId(Long categorieId);
}
