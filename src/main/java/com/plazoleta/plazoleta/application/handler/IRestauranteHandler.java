package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.RestauranteListaResponseDto;
import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;

import java.util.List;

public interface IRestauranteHandler {
    void crearRestaurante(RestauranteRequestDto restauranteRequestDto);
    RestauranteResponseDto findRestauranteById(Long idRestaurante);
    List<RestauranteListaResponseDto> listarRestaurantesOrdenadosPorNombre(int page, int size);
}
