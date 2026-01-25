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

@ControllerAdvice
public class GlobalExceptionHandler {

    private String obtenerFechaActual() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Método auxiliar para construir la respuesta
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

    // 1. Caso: ID No encontrado (404)
    @ExceptionHandler(FindIdException.class)
    public ResponseEntity<ErrorResponse> manejarFindId(FindIdException ex, HttpServletRequest request) {
        return crearRespuestaError("Recurso no encontrado", ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    // 2. Caso: JSON mal formado o tipos de datos incorrectos (400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarErrorLectura(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return crearRespuestaError("Error de formato", "JSON inválido o tipos de datos incorrectos.", HttpStatus.BAD_REQUEST, request);
    }

    // 3. Caso: Error en @Valid del DTO (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacionDto(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return crearRespuestaError("Validación fallida (DTO)", detalles, HttpStatus.BAD_REQUEST, request);
    }

    // 4. Caso: Errores de validación de la Entidad (POST, PUT, PATCH)
    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public ResponseEntity<ErrorResponse> manejarErroresValidacionEntidad(Exception ex, HttpServletRequest request) {
        String mensaje = "Error de integridad en los datos.";

        Throwable cause = (ex instanceof TransactionSystemException) ? ((TransactionSystemException) ex).getRootCause() : ex;

        if (cause instanceof ConstraintViolationException) {
            mensaje = ((ConstraintViolationException) cause).getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
        }

        return crearRespuestaError("Violación de restricciones", mensaje, HttpStatus.BAD_REQUEST, request);
    }

    // 5. Caso General: Errores 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGenerico(Exception ex, HttpServletRequest request) {
        return crearRespuestaError("Error interno", "Ocurrió un fallo inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}