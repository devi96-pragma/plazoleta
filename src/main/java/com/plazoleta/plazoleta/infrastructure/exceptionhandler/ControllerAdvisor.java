package com.plazoleta.plazoleta.infrastructure.exceptionhandler;

import com.plazoleta.plazoleta.domain.exception.PlatoNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEsDelUsuarioException;
import com.plazoleta.plazoleta.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.plazoleta.infrastructure.exception.UsuarioNoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "message";
    @ExceptionHandler(UsuarioNoEsPropietarioException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEsPropietarioException(UsuarioNoEsPropietarioException e) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEncontradoException(UsuarioNoEncontradoException e) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(RestauranteNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleRestauranteNoEncontradoException(RestauranteNoEncontradoException e) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    @ExceptionHandler(RestauranteNoEsDelUsuarioException.class)
    public ResponseEntity<Map<String, String>> handleRestauranteNoEsDelUsuarioException(RestauranteNoEsDelUsuarioException e) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    @ExceptionHandler(PlatoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handlePlatoNoEncontradoException(PlatoNoEncontradoException e) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
