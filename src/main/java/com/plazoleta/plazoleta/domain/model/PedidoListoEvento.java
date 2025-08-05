package com.plazoleta.plazoleta.domain.model;

import java.math.BigDecimal;

public class PedidoListoEvento {
    private Long idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String numeroTelefono;
    private BigDecimal montoTotal;
    private String pin;
    private Long idPedido;
    public PedidoListoEvento() {
    }

    public PedidoListoEvento(Long idCliente, String nombreCliente, String apellidoCliente, String numeroTelefono, BigDecimal montoTotal, String pin, Long idPedido) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.numeroTelefono = numeroTelefono;
        this.montoTotal = montoTotal;
        this.pin = pin;
        this.idPedido = idPedido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
}
