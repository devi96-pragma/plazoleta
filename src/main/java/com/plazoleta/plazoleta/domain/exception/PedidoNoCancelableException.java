package com.plazoleta.plazoleta.domain.exception;

public class PedidoNoCancelableException extends RuntimeException {
    public PedidoNoCancelableException(String message) {
        super(message);
    }
}
