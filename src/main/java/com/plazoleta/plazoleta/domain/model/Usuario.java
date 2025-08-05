package com.plazoleta.plazoleta.domain.model;

import java.time.LocalDate;

public class Usuario {
    private Long id;
    private String nombre;
    private String apellido;
    private Integer documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
    private Rol rol;

    public Usuario(){}
    public Usuario(Long id, Rol rol, String clave, String correo, LocalDate fechaNacimiento, String celular, Integer documentoDeIdentidad, String apellido, String nombre) {
        this.id = id;
        this.rol = rol;
        this.clave = clave;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.documentoDeIdentidad = documentoDeIdentidad;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDocumentoDeIdentidad() {
        return documentoDeIdentidad;
    }

    public void setDocumentoDeIdentidad(Integer documentoDeIdentidad) {
        this.documentoDeIdentidad = documentoDeIdentidad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
