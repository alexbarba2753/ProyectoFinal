package edu.epn.proyectofinal.inventarioproductos.exception;

/**
 * Excepción personalizada para conflictos de duplicidad.
 * Se lanza cuando el Service detecta que un Nombre e Imagen ya existen.
 */
public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String nombre) {
        // Pasa el mensaje personalizado a la clase padre RuntimeException
        super("El producto '" + nombre + "' ya existe en el inventario.");
    }
}