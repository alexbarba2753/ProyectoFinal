package edu.epn.proyectofinal.inventarioproductos.exception;

/**
 * Excepción personalizada para búsquedas fallidas.
 * Se dispara cuando un ID solicitado no existe en la base de datos H2.
 */
public class FindIdException extends RuntimeException {
    public FindIdException(Long id) {
        super("No se encontró el producto con el ID: " + id);
    }
}