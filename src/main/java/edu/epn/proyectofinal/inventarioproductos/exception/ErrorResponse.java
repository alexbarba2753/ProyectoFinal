package edu.epn.proyectofinal.inventarioproductos.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase POJO (Plain Old Java Object): Define la estructura JSON de los errores.
 * Garantiza que todas las respuestas de error de la API tengan el mismo formato.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String timestamp; // Fecha y hora del error
    private int status;       // Código HTTP (400, 404, 409, 500)
    private String error;      // Título breve del error
    private String message;    // Descripción detallada para el usuario
    private String path;       // Endpoint donde ocurrió el fallo (URI)
}