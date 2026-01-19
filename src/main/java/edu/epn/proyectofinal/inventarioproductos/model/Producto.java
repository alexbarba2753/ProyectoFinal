package edu.epn.proyectofinal.inventarioproductos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El nombre del anime no puede estar vacío")
    private String anime;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.1",message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El stock no puede estar vacío")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private String imagenUrl;
}
