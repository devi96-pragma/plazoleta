package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.RestauranteListaResponseDto;
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

import java.util.List;

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
    @Test
    public void listarRestaurantesOrdenadosPorNombre_conParametrosValidos() {
        // Arrange
        int page = 0;
        int size = 10;
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setId(1L);
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setId(2L);

        RestauranteListaResponseDto restauranteDto1 = new RestauranteListaResponseDto();
        RestauranteListaResponseDto restauranteDto2 = new RestauranteListaResponseDto();

        List<RestauranteListaResponseDto> restaurantesDto = List.of(restauranteDto1, restauranteDto2);
        List<Restaurante> restaurantes = List.of(restaurante1, restaurante2);
        //Devuelves la lista de restaurantes ordenados por nombre
        when(restauranteServicePort.listarRestaurantesOrdenadosPorNombre(0,10)).thenReturn(restaurantes);
        //Conviertes la lista de restaurantes a una lista de DTOs
        when(restauranteResponseMapper.toResponseList(restaurantes)).thenReturn(restaurantesDto);
        // Act
        restauranteHandler.listarRestaurantesOrdenadosPorNombre(page, size);

        // Assert
        verify(restauranteResponseMapper, times(1)).toResponseList(restaurantes);
        verify(restauranteServicePort, times(1)).listarRestaurantesOrdenadosPorNombre(page, size);
    }
}
