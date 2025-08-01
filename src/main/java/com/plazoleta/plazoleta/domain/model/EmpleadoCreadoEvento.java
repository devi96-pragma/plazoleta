package com.plazoleta.plazoleta.domain.model;

public class EmpleadoCreadoEvento {

    private Long idUsuario;
    private Long idRestaurante;

    public EmpleadoCreadoEvento(){}
    public EmpleadoCreadoEvento(Long idUsuario, Long idRestaurante) {
        this.idUsuario = idUsuario;
        this.idRestaurante = idRestaurante;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
}
