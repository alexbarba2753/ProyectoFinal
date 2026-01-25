package edu.epn.proyectofinal.inventarioproductos.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Interceptor Global de Excepciones.
 * Utiliza @ControllerAdvice para capturar errores en cualquier parte de la aplicación
 * y transformarlos en una respuesta JSON estandarizada.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Formateador de fecha para el timestamp del error
    private String obtenerFechaActual() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Método centralizado para construir objetos ResponseEntity.
     * Optimiza el código evitando la repetición de instanciación de ErrorResponse.
     */
    private ResponseEntity<ErrorResponse> crearRespuestaError(String titulo, String mensaje, HttpStatus status, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                obtenerFechaActual(),
                status.value(),
                titulo,
                mensaje,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    /**
     * Captura cuando no se encuentra un ID en la base de datos (404 Not Found).
     */
    @ExceptionHandler(FindIdException.class)
    public ResponseEntity<ErrorResponse> manejarFindId(FindIdException ex, HttpServletRequest request) {
        return crearRespuestaError("Recurso no encontrado", ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Captura errores de sintaxis JSON o tipos de datos incompatibles (400 Bad Request).
     * Ejemplo: Enviar texto en un campo numérico.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarErrorLectura(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return crearRespuestaError("Error de formato", "JSON inválido o tipos de datos incorrectos.", HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Captura errores de validación en la capa DTO mediante @Valid (400 Bad Request).
     * Recolecta todos los campos fallidos en un solo mensaje.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacionDto(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return crearRespuestaError("Validación fallida (DTO)", detalles, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Maneja errores de integridad provenientes de la Entidad o la Transacción (400 Bad Request).
     * Se encarga de "desenvolver" la excepción para encontrar la causa raíz (Root Cause).
     */
    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public ResponseEntity<ErrorResponse> manejarErroresValidacionEntidad(Exception ex, HttpServletRequest request) {
        String mensaje = "Error de integridad en los datos.";

        // Si el error viene de una transacción, extraemos la causa real
        Throwable cause = (ex instanceof TransactionSystemException) ? ((TransactionSystemException) ex).getRootCause() : ex;

        // Si la causa es una violación de restricciones, extraemos los mensajes específicos
        if (cause instanceof ConstraintViolationException) {
            mensaje = ((ConstraintViolationException) cause).getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
        }

        return crearRespuestaError("Violación de restricciones", mensaje, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Captura intentos de crear o actualizar productos con Nombre e Imagen ya existentes (409 Conflict).
     */
    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<ErrorResponse> manejarDuplicados(DuplicateProductException ex, HttpServletRequest request) {
        return crearRespuestaError(
                "Conflicto de duplicidad",
                ex.getMessage(),
                HttpStatus.CONFLICT,
                request
        );
    }

    /**
     * "Atrapa-todo": Captura cualquier excepción no controlada (500 Internal Server Error).
     * Evita que la API exponga trazas de error internas al usuario final.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGenerico(Exception ex, HttpServletRequest request) {
        return crearRespuestaError("Error interno", "Ocurrió un fallo inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}