package edu.epn.proyectofinal.inventarioproductos.controller;

import edu.epn.proyectofinal.inventarioproductos.dto.ProductoDTO;
import edu.epn.proyectofinal.inventarioproductos.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Capa de Controlador (REST Controller): Punto de entrada de la API.
 * Se encarga de exponer los recursos (endpoints) y gestionar las peticiones HTTP.
 */
@RestController
@RequestMapping("/api")
public class ProductoController {

    // Inyección de la interfaz del servicio por constructor
    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Obtener todos los productos.
     * @return Lista de ProductoDTO con estado 200 OK.
     */
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    /**
     * Obtener un producto específico por su ID.
     * @param id Identificador único del producto.
     * @return ProductoDTO encontrado con estado 200 OK.
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    /**
     * Crear un nuevo producto.
     * @param dto Datos del producto validados mediante @Valid.
     * @return ProductoDTO creado con estado 201 CREATED.
     */
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        // Se utiliza HttpStatus.CREATED (201) por estándar REST al crear recursos
        return new ResponseEntity<>(productoService.guardar(dto), HttpStatus.CREATED);
    }

    /**
     * Actualización Total (PUT): Reemplaza el recurso existente.
     * @param id ID del producto a reemplazar.
     * @param dto Nuevos datos (deben estar completos y válidos).
     */
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> editar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productoService.actualizarCompleto(id, dto));
    }

    /**
     * Actualización Parcial (PATCH): Modifica atributos específicos.
     * No utiliza @Valid obligatoriamente para permitir el envío de campos nulos.
     */
    @PatchMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> editarUnit(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productoService.actualizarParcial(id, dto));
    }

    /**
     * Eliminar un producto.
     * @param id ID del producto a remover.
     * @return Respuesta vacía con estado 204 NO CONTENT.
     */
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        // El estado 204 indica que la acción se realizó con éxito pero no hay contenido que devolver
        return ResponseEntity.noContent().build();
    }
}