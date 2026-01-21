package edu.epn.proyectofinal.inventarioproductos.controller;

import edu.epn.proyectofinal.inventarioproductos.dto.ProductoDTO;
import edu.epn.proyectofinal.inventarioproductos.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    // Inyección de la interfaz del servicio mediante el constructor
    private final IProductoService productoService;

    private  ProductoController (IProductoService productoService){
        this.productoService = productoService;
    }

    // Get all productos
    @GetMapping ("/productos")
    public ResponseEntity<List<ProductoDTO>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    // Get producto by ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    // Post crear nuevo producto
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        return new ResponseEntity<>(productoService.guardar(dto), HttpStatus.CREATED);
    }

    // Put actualizar producto existente
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> editar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    // Patch actualizar producto existente
    @PatchMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> editarUnit(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    // Delete producto por ID
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}