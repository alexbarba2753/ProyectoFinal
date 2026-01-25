package edu.epn.proyectofinal.inventarioproductos.service;

import edu.epn.proyectofinal.inventarioproductos.dto.ProductoDTO;
import edu.epn.proyectofinal.inventarioproductos.exception.DuplicateProductException;
import edu.epn.proyectofinal.inventarioproductos.exception.FindIdException;
import edu.epn.proyectofinal.inventarioproductos.model.Producto;
import edu.epn.proyectofinal.inventarioproductos.repository.IProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Capa de Servicio: Contiene la lógica de negocio de la aplicación.
 * Actúa como puente entre el Controlador (API) y el Repositorio (Base de Datos).
 */
@Service
public class ProductoService implements IProductoService {

    private final IProductoRepository productoRepository;

    // Inyección de dependencias por constructor
    public ProductoService(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    /**
     * Obtiene todos los productos y los transforma de Entidad a DTO.
     * Uso de Streams para procesar la lista de forma funcional.
     */
    @Override
    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto por ID. Si no existe, lanza una excepción personalizada (404).
     */
    @Override
    public ProductoDTO buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new FindIdException(id));
        return convertirADto(producto);
    }

    /**
     * Registra un nuevo producto validando primero que no sea un duplicado (Nombre + Imagen).
     */
    @Override
    public ProductoDTO guardar(ProductoDTO productoDTO) {
        // Lógica de Negocio: Evitar duplicados exactos en el catálogo
        if (productoRepository.existsByNombreAndImagenUrl(
                productoDTO.getNombre().trim(),
                productoDTO.getImagenUrl().trim())) {
            throw new DuplicateProductException(productoDTO.getNombre());
        }

        // Conversión de datos y persistencia
        Producto producto = convertirAEntidad(productoDTO);
        return convertirADto(productoRepository.save(producto));
    }

    /**
     * Actualización Parcial (PATCH): Solo modifica los campos que el usuario envía.
     */
    @Override
    public ProductoDTO actualizarParcial(Long id, ProductoDTO productoDTO) {
        // Verificamos existencia antes de operar
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new FindIdException(id));

        // Validamos que el cambio no colisione con otro producto existente
        validarDuplicadoParaUpdate(id, productoDTO.getNombre(), productoDTO.getImagenUrl(), producto);

        // Actualización selectiva (Null-Safe)
        if (productoDTO.getNombre() != null && !productoDTO.getNombre().isBlank())
            producto.setNombre(productoDTO.getNombre());

        if (productoDTO.getAnime() != null && !productoDTO.getAnime().isBlank())
            producto.setAnime(productoDTO.getAnime());

        if (productoDTO.getPrecio() != null)
            producto.setPrecio(productoDTO.getPrecio());

        if (productoDTO.getStock() != null)
            producto.setStock(productoDTO.getStock());

        if (productoDTO.getImagenUrl() != null && !productoDTO.getImagenUrl().isBlank())
            producto.setImagenUrl(productoDTO.getImagenUrl());

        return convertirADto(productoRepository.save(producto));
    }

    /**
     * Actualización Completa (PUT): Reemplaza todos los valores del recurso.
     */
    @Override
    public ProductoDTO actualizarCompleto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new FindIdException(id));

        validarDuplicadoParaUpdate(id, dto.getNombre(), dto.getImagenUrl(), producto);

        // Reemplazo total de atributos
        producto.setNombre(dto.getNombre());
        producto.setAnime(dto.getAnime());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());

        return convertirADto(productoRepository.save(producto));
    }

    /**
     * Lógica de Validación de Duplicidad:
     * Comprueba si el nombre e imagen ya existen en OTRO registro (excluyendo el actual).
     */
    private void validarDuplicadoParaUpdate(Long idActual, String nombreNuevo, String imagenNueva, Producto productoActual) {
        // Si el valor viene nulo en el DTO (PATCH), usamos el valor que ya tiene la entidad
        String nombreAValidar = (nombreNuevo != null) ? nombreNuevo : productoActual.getNombre();
        String imagenAValidar = (imagenNueva != null) ? imagenNueva : productoActual.getImagenUrl();

        // Consulta al repositorio excluyendo el ID que estamos editando
        boolean existeDuplicado = productoRepository.existsByNombreAndImagenUrlAndIdNot(nombreAValidar, imagenAValidar, idActual);

        if (existeDuplicado) {
            throw new DuplicateProductException(nombreAValidar);
        }
    }

    /**
     * Elimina un producto por ID. Valida existencia antes para lanzar 404 si falla.
     */
    @Override
    public void eliminar(Long id) {
        productoRepository.findById(id)
                .orElseThrow(() -> new FindIdException(id));
        productoRepository.deleteById(id);
    }

    // --- MÉTODOS DE MAPEO (MANUAL MAPPING) ---

    // Entidad a DTO
    private ProductoDTO convertirADto(Producto p) {
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
    private Producto convertirAEntidad(ProductoDTO dto) {
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