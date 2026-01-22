package edu.epn.proyectofinal.inventarioproductos.dto;

import jakarta.validation.constraints.*; // Importante agregar esto
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
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
