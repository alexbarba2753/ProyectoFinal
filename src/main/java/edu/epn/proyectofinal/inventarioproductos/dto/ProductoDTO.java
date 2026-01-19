package edu.epn.proyectofinal.inventarioproductos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductoDTO {
    private Long id;
    private String nombre;
    private String anime;
    private Double precio;
    private String imagenUrl;
    private Integer stock;
}
