package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;
import com.plazoleta.plazoleta.application.mapper.IRestauranteRequestMapper;
import com.plazoleta.plazoleta.application.mapper.IRestauranteResponseMapper;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestauranteHandlerTest {
    @InjectMocks
    private RestauranteHandler restauranteHandler;
    @Mock
    private IRestauranteServicePort restauranteServicePort;
    @Mock
    private IRestauranteRequestMapper restauranteRequestMapper;
    @Mock
    private IRestauranteResponseMapper restauranteResponseMapper;
    @Test
    public void crearRestaurante_conDatosValidos() {
        // Arrange
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        Restaurante restaurante = new Restaurante();
        when(restauranteRequestMapper.toDomain(requestDto)).thenReturn(restaurante);

        // Act
        restauranteHandler.crearRestaurante(requestDto);

        // Assert
        verify(restauranteRequestMapper, times(1)).toDomain(requestDto);
        verify(restauranteServicePort, times(1)).crearRestaurante(restaurante);
    }
    @Test
    public void encontrarRestaurantePorId_conIdValido() {
        // Arrange
        Long idRestaurante = 1L;
        Restaurante restaurante = new Restaurante();
        RestauranteResponseDto dto = new RestauranteResponseDto();
        when(restauranteServicePort.findRestauranteById(idRestaurante)).thenReturn(restaurante);
        when(restauranteResponseMapper.toResponse(restaurante)).thenReturn(dto);
        // Act
        restauranteHandler.findRestauranteById(idRestaurante);

        // Assert
        verify(restauranteServicePort, times(1)).findRestauranteById(idRestaurante);
    }
}
