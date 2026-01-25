package edu.epn.proyectofinal.inventarioproductos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Clase de Entidad: Representa la tabla "productos" en la base de datos H2.
 * Utiliza anotaciones de JPA para el mapeo y de Jakarta para la validación de integridad.
 */
@Getter @Setter                // Genera automáticamente los métodos Getters y Setters (Lombok)
@NoArgsConstructor             // Genera el constructor vacío obligatorio para JPA (Lombok)
@AllArgsConstructor            // Genera el constructor con todos los campos (Lombok)
@Entity                        // Marca esta clase como una entidad persistente de base de datos
@Table(name = "productos")     // Define el nombre físico de la tabla en el motor H2
public class Producto {

    @Id                        // Define la Llave Primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID es auto-incremental
    private Long id;

    // Validación a nivel de persistencia: El campo no debe ser nulo ni solo espacios
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El nombre del anime no puede estar vacío")
    private String anime;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.1", message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El stock no puede estar vacío")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    // @Column se usa para mapear el nombre del atributo en Java con el de la columna en SQL
    // camelCase (Java) a snake_case (SQL)
    @Column(name = "imagen_url")
    @NotNull(message = "La imagenUrl no puede estar vacía")
    private String imagenUrl;
}