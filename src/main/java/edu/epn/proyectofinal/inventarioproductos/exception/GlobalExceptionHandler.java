package edu.epn.proyectofinal.inventarioproductos.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler {

    private String obtenerFechaActual() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // 1. Caso: ID No encontrado (404)
    @ExceptionHandler(FindIdException.class)
    public ResponseEntity<ErrorResponse> manejarFindId(FindIdException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                obtenerFechaActual(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 2. Caso: JSON mal formado o letras en números (400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarErrorLectura(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                obtenerFechaActual(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de formato de datos",
                "Se esperaba un valor numérico o un formato JSON válido.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 3. Caso: Fallo en @Valid del DTO (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String mensajesDeTodosLosCampos = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
                obtenerFechaActual(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación de campos",
                "Errores detectados: " + mensajesDeTodosLosCampos,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 4. Caso: Validación de Entidad durante la transacción (Precios/Stock negativos)
    @ExceptionHandler(org.springframework.transaction.TransactionSystemException.class)
    public ResponseEntity<ErrorResponse> manejarErrorTransaccion(org.springframework.transaction.TransactionSystemException ex, HttpServletRequest request) {
        String mensajeResumen = "Error de validación en la base de datos (posible valor negativo o campo inválido).";

        // Intentamos extraer el mensaje original de la entidad (ej: "El precio debe ser mayor a 0")
        Throwable cause = ex.getRootCause();
        if (cause instanceof jakarta.validation.ConstraintViolationException) {
            mensajeResumen = ((jakarta.validation.ConstraintViolationException) cause).getConstraintViolations()
                    .stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(java.util.stream.Collectors.joining(", "));
        }

        ErrorResponse error = new ErrorResponse(
                obtenerFechaActual(),
                HttpStatus.BAD_REQUEST.value(),
                "Violación de Restricciones",
                mensajeResumen,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 5. Caso AtrapaTodo: Errores inesperados (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionGenerica(Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                obtenerFechaActual(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                "Ocurrió un error inesperado: " + ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}