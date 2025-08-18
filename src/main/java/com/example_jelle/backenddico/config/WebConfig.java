package com.example_jelle.backenddico.config; // Pas dit aan naar jouw package naam

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Pas dit eventueel aan als je API-pad anders is
                .allowedOrigins("http://localhost:5173") // De precieze URL van je frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Toegestane methodes
                .allowedHeaders("*") // Toegestane headers
                .allowCredentials(true); // Sta credentials toe
    }
}

////        Remote
//        // ...
//        registry.addMapping("/**")
//                .allowedOrigins("*") // Staat alle domeinen toe
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowCredentials(false); // Credentials kunnen niet met "*"
//// ...


