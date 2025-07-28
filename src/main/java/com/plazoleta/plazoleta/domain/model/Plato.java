package com.plazoleta.plazoleta.domain.model;

public class Plato {
    private Long id;
    private String nombre;
    private float precio;
    private String descripcion;
    private String urlImagen;
    private Categoria categoria;
    private boolean isActivo;
    private Long idRestaurante;

    public Plato() {
    }
    public Plato(Long id, String nombre, float precio, String descripcion, String urlImagen, Categoria categoria, boolean isActivo, Long idRestaurante) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.isActivo = isActivo;
        this.idRestaurante = idRestaurante;
    }
    public void actualizarDesde(Plato nuevo) {
        this.precio = nuevo.getPrecio();
        this.descripcion = nuevo.getDescripcion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActivo() {
        return isActivo;
    }

    public void setActivo(boolean activo) {
        isActivo = activo;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
