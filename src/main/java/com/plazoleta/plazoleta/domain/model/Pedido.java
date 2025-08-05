package com.plazoleta.plazoleta.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private Long id;
    private Long idRestaurante;
    private Long idUsuario;// EL Id del cliente
    private EstadoPedido estado;
    private LocalDateTime fechaCreacion;
    private List<PedidoPlato> platos;
    private BigDecimal precioTotal;
    private Long idEmpleado;
    private String pin;
    public Pedido(Long id, Long idRestaurante, Long idUsuario, EstadoPedido estado, LocalDateTime fechaCreacion, List<PedidoPlato> platos) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.platos = platos;
        //calcularPrecioTotal();
    }

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<PedidoPlato> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PedidoPlato> platos) {
        this.platos = platos;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }
    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void calcularPrecioTotal() {
        this.precioTotal = this.platos.stream()
                .map(PedidoPlato::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
