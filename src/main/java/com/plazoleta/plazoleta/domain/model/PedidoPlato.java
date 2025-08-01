package com.plazoleta.plazoleta.domain.model;

import java.math.BigDecimal;

public class PedidoPlato {
    private Long idPlato;
    private String nombrePlato;
    private BigDecimal precioUnitario;
    private Integer cantidad;

    public PedidoPlato() {
    }
    public PedidoPlato(Long idPlato, String nombrePlato, BigDecimal precioUnitario, Integer cantidad) {
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public Long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Long idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
