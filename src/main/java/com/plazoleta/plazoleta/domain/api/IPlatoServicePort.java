package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;

import java.util.List;

public interface IPlatoServicePort {
    void crearPlato(Plato plato);
    Plato actualizarPlato(Plato plato, Long idPlato);
    Plato obtenerPlatoPorId(Long idPlato);
    Plato habilitarDeshabilitarPlato(Long idPlato, boolean estado);
    List<Plato> listarPlatosPorRestaurante(Long idRestaurante, int page, int size, String categoria);
}
