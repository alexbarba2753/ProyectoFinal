package edu.epn.proyectofinal.inventarioproductos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Maneja excepciones globalmente en los controladores
public class GlobalExceptionHandler {
    @ExceptionHandler(FindIdException.class)
    public ResponseEntity<Map<String, String>> manejarFindId(FindIdException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Recurso no encontrado");
        error.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // Código 404
    }
}
