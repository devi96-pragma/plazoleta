package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.RestauranteListaResponseDto;
import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;
import com.plazoleta.plazoleta.application.mapper.IRestauranteRequestMapper;
import com.plazoleta.plazoleta.application.mapper.IRestauranteResponseMapper;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestauranteHandler implements IRestauranteHandler {
    private final IRestauranteServicePort restauranteServicePort;
    private final IRestauranteRequestMapper restauranteRequestMapper;
    private final IRestauranteResponseMapper restauranteResponseMapper;

    public void crearRestaurante(RestauranteRequestDto restauranteRequestDto) {
        Restaurante restaurante = restauranteRequestMapper.toDomain(restauranteRequestDto);
        restauranteServicePort.crearRestaurante(restaurante);
    }
    public RestauranteResponseDto findRestauranteById(Long idRestaurante) {
        return restauranteResponseMapper.toResponse(
                restauranteServicePort.findRestauranteById(idRestaurante)
        );
    }

    @Override
    public List<RestauranteListaResponseDto> listarRestaurantesOrdenadosPorNombre(int page, int size) {
        return restauranteResponseMapper.toResponseList(
                restauranteServicePort.listarRestaurantesOrdenadosPorNombre(page, size)
        );
    }
}
