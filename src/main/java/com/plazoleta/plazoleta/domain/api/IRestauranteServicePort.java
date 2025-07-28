package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.model.Restaurante;

public interface IRestauranteServicePort {
    void crearRestaurante(Restaurante restaurante);
    Restaurante findRestauranteById(Long idRestaurante);
}
