package com.plazoleta.plazoleta.infrastructure.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.plazoleta.plazoleta.domain.constantes.Constantes;
import com.plazoleta.plazoleta.domain.exception.*;
import com.plazoleta.plazoleta.infrastructure.exception.UsuarioNoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "message";

    private ResponseEntity<Map<String, String>> buildResponse(String mensaje, HttpStatus status) {
        return new ResponseEntity<>(
                Collections.singletonMap(MESSAGE, mensaje),
                status
        );
    }

    @ExceptionHandler(UsuarioNoEsPropietarioException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEsPropietarioException(UsuarioNoEsPropietarioException e) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEncontradoException(UsuarioNoEncontradoException e) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestauranteNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleRestauranteNoEncontradoException(RestauranteNoEncontradoException e) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RestauranteNoEsDelUsuarioException.class)
    public ResponseEntity<Map<String, String>> handleRestauranteNoEsDelUsuarioException(RestauranteNoEsDelUsuarioException e) {
        return buildResponse(e.getMessage(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(PlatoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handlePlatoNoEncontradoException(PlatoNoEncontradoException e) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PlatoYaEnEstadoSolicitado.class)
    public ResponseEntity<Map<String, String>> handlePlatoYaEnEstadoSolicitadoException(PlatoYaEnEstadoSolicitado e) {
        return buildResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(CategoriaInvalidaException.class)
    public ResponseEntity<Map<String, String>> handleCategoriaInvalida(CategoriaInvalidaException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonParseError(HttpMessageNotReadableException ex) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        if (mostSpecificCause instanceof InvalidFormatException && ex.getMessage().contains("Categoria")) {
            return buildResponse(Constantes.MensajesError.CATEGORIA_NO_ENCONTRADA, HttpStatus.BAD_REQUEST);
        }
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
