package edu.epn.proyectofinal.inventarioproductos.repository;

import edu.epn.proyectofinal.inventarioproductos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository <Producto, Long> {
    // Aquí ya tenemos métodos como save(), findAll(), deleteById(), etc.
}
