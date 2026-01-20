package edu.epn.proyectofinal.inventarioproductos.service;

import edu.epn.proyectofinal.inventarioproductos.dto.ProductoDTO;
import edu.epn.proyectofinal.inventarioproductos.exception.FindIdException;
import edu.epn.proyectofinal.inventarioproductos.model.Producto;
import edu.epn.proyectofinal.inventarioproductos.repository.IProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService{


    private final IProductoRepository productoRepository;

    public ProductoService(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new FindIdException(id));
        return convertirADto(producto);
    }

    @Override
    public ProductoDTO guardar(ProductoDTO productoDTO) {
        Producto producto = convertirAEntidad(productoDTO);
        return convertirADto(productoRepository.save(producto));
    }

    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new FindIdException(id));

        if (productoDTO.getNombre()!= null)
            producto.setNombre(productoDTO.getNombre());

        if (productoDTO.getAnime()!= null)
            producto.setAnime(productoDTO.getAnime());

        if (productoDTO.getPrecio()!= null)
            producto.setPrecio(productoDTO.getPrecio());

        if (productoDTO.getStock()!= null)
            producto.setStock(productoDTO.getStock());

        if (productoDTO.getImagenUrl()!= null)
            producto.setImagenUrl(productoDTO.getImagenUrl());

        return convertirADto(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    // Mapeos
    // Entidad a DTO
    private ProductoDTO convertirADto(Producto p){
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setAnime(p.getAnime());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        dto.setImagenUrl(p.getImagenUrl());
        return dto;
    }

    // DTO a Entidad
    private Producto convertirAEntidad(ProductoDTO dto){
        Producto p = new Producto();
        p.setId(dto.getId());
        p.setNombre(dto.getNombre());
        p.setAnime(dto.getAnime());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setImagenUrl(dto.getImagenUrl());
        return p;
    }
}
