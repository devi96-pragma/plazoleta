package com.plazoleta.plazoleta.domain.exception;

public class UsuarioNoEsPropietarioException extends RuntimeException {
    public UsuarioNoEsPropietarioException(String message) {
        super(message);
    }
}
