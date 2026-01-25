package edu.epn.proyectofinal.inventarioproductos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Clase DTO (Data Transfer Object): Objeto de transporte de datos.
 * Se utiliza para mover la información entre el Frontend y el Backend sin
 * exponer directamente la estructura de la base de datos (Entidad).
 */
@Getter @Setter                // Generación automática de accesores (Lombok)
@NoArgsConstructor             // Constructor sin argumentos necesario para la deserialización de JSON
@AllArgsConstructor            // Constructor con todos los campos para facilitar pruebas
public class ProductoDTO {

    // El ID puede ser nulo en peticiones POST (Creación)
    private Long id;

    // Jakarta Validation: Estas etiquetas detienen la petición en el Controller si no se cumplen
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El anime es obligatorio")
    private String anime;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    private Integer stock;

    @NotNull(message = "La imagenUrl es obligatoria")
    private String imagenUrl;
}