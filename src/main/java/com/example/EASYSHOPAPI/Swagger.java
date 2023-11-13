package com.example.EASYSHOPAPI;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class Swagger {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("API POUR LA GESTION DE MISE EN RELATION CLIRNT-FOURNISSEUR")
                        .description("Elle est conçue pour faciliter l'approvisonnement des produits pour les entreprises et particuliers")
                        .version("1.0"));
    }
}
