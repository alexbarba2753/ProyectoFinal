package edu.epn.proyectofinal.inventarioproductos.service;

import edu.epn.proyectofinal.inventarioproductos.dto.ProductoDTO;
import java.util.List;

/**
 * Interfaz de Servicio (IProductoService): Define el contrato de los métodos de negocio.
 * Sigue el principio de Inversión de Dependencias (DIP) de SOLID, permitiendo
 * desacoplar la definición de las acciones de su implementación real.
 */
public interface IProductoService {

    /**
     * Recupera la lista completa de productos transformados en DTOs.
     */
    List<ProductoDTO> listarTodos();

    /**
     * Recupera un único producto basado en su identificador único.
     */
    ProductoDTO buscarPorId(Long id);

    /**
     * Define la lógica para persistir un nuevo producto en el sistema.
     */
    ProductoDTO guardar(ProductoDTO productoDTO);

    /**
     * Define la actualización de campos específicos de un producto (PATCH).
     */
    ProductoDTO actualizarParcial(Long id, ProductoDTO productoDTO);

    /**
     * Define la sustitución completa de un producto existente (PUT).
     */
    ProductoDTO actualizarCompleto(Long id, ProductoDTO productoDTO);

    /**
     * Define la eliminación lógica o física de un producto del inventario.
     */
    void eliminar(Long id);
}