package com.plazoleta.plazoleta.domain.model;

import java.time.LocalDateTime;

public class PedidoEstadoCambiadoEvento {

    Long idPedido;
    Long idCliente;
    Long idEmpleado;
    EstadoPedido estado;
    LocalDateTime fecha;

    public PedidoEstadoCambiadoEvento(){}

    public PedidoEstadoCambiadoEvento(Long idPedido, Long idCliente,Long idEmpleado, EstadoPedido estado, LocalDateTime fecha) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
