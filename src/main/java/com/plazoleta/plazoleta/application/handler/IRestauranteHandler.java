package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;

public interface IRestauranteHandler {
    void crearRestaurante(RestauranteRequestDto restauranteRequestDto);
    RestauranteResponseDto findRestauranteById(Long idRestaurante);
}
