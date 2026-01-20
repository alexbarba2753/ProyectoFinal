package edu.epn.proyectofinal.inventarioproductos.exception;


public class FindIdException extends RuntimeException {
    public FindIdException(Long id) {
        super("No se encontró el producto con el ID: " + id);
    }
}