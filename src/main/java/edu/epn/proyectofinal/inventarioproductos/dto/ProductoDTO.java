package edu.epn.proyectofinal.inventarioproductos.dto;

import jakarta.validation.constraints.*; // Importante agregar esto
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50)
    private String nombre;

    @NotBlank(message = "El nombre del anime es obligatorio")
    private String anime;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.1")
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(0)
    private Integer stock;

    private String imagenUrl;
}