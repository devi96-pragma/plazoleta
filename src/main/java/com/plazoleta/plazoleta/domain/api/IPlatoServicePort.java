package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Plato;

public interface IPlatoServicePort {
    void crearPlato(Plato plato);
    Plato actualizarPlato(Plato plato, Long idPlato);
    Plato obtenerPlatoPorId(Long idPlato);
}
