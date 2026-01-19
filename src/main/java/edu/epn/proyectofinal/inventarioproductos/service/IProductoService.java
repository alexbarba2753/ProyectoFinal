package edu.epn.proyectofinal.inventarioproductos.service;

import edu.epn.proyectofinal.inventarioproductos.dto.ProductoDTO;

import java.util.List;

public interface IProductoService {
    List<ProductoDTO> listarTodos();
    ProductoDTO buscarPorId(Long id);
    ProductoDTO guardar(ProductoDTO productoDTO);
    ProductoDTO actualizar(Long id, ProductoDTO productoDTO);
    void eliminar(Long id);
}
