package edu.epn.proyectofinal.inventarioproductos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de Configuración: Define las políticas de intercambio de recursos de origen cruzado (CORS).
 * Permite que el Frontend (Vercel) se comunique con el Backend (Render).
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Se aplica la configuración a todas las rutas que empiecen con /api/
                registry.addMapping("/api/**")
                        // Se permiten peticiones desde cualquier origen (cambiar a dominio específico en producción)
                        .allowedOrigins("*")
                        // Se habilitan todos los verbos HTTP necesarios para el CRUD
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        // Se permite cualquier tipo de cabecera (Headers) como Content-Type o Authorization
                        .allowedHeaders("*");
            }
        };
    }
}