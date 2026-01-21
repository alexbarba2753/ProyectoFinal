package edu.epn.proyectofinal.inventarioproductos.dto;

import jakarta.validation.constraints.*; // Importante agregar esto
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String anime;
    private Double precio;
    private Integer stock;
    private String imagenUrl;
}