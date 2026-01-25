package edu.epn.proyectofinal.inventarioproductos.repository;

import edu.epn.proyectofinal.inventarioproductos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Capa de Persistencia: Gestiona todas las operaciones CRUD sobre la tabla 'productos'.
 * Al extender de JpaRepository, obtenemos métodos automáticos de persistencia.
 */
@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Query Derivada: Busca si existe un producto con el mismo nombre e imagen.
     * Útil para validar duplicados en la creación (POST).
     */
    boolean existsByNombreAndImagenUrl(String nombre, String imagenUrl);

    /**
     * Query Derivada Avanzada: Busca duplicados ignorando el registro actual.
     * Útil para actualizaciones (PUT/PATCH), evitando que el producto choque consigo mismo.
     */
    boolean existsByNombreAndImagenUrlAndIdNot(String nombre, String imagenUrl, Long id);
}