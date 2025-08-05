package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;
import com.plazoleta.plazoleta.application.mapper.IPlatoCreateRequestMapper;
import com.plazoleta.plazoleta.application.mapper.IPlatoResponseMapper;
import com.plazoleta.plazoleta.application.mapper.IPlatoUpdateRequestMapper;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlatoHandlerTest {
    @Mock
    private IPlatoServicePort platoServicePort;
    @Mock
    private IPlatoResponseMapper platoResponseMapper;
    @Mock
    private IPlatoCreateRequestMapper platoCreateRequestMapper;
    @Mock
    private IPlatoUpdateRequestMapper platoUpdateRequestMapper;
    @InjectMocks
    private PlatoHandler platoHandler;

    //Test happy path: Crear plato con datos válidos
    @Test
    public void testCrearPlato_conDatosValidos_Exito() {
        //arrange
        PlatoCreateRequestDto dto = new PlatoCreateRequestDto(
                "Limonada",
                10,
                "Descripción del plato",
                "https://example.com/image.jpg",
                Categoria.BEBIDA,
                1L
                );
        Plato plato = new Plato(
                null,
                "Limonada",
                10,
                "Descripción del plato",
                "https://example.com/image.jpg",
                Categoria.BEBIDA,
                false,
                1L
                );
        when(platoCreateRequestMapper.toDomain(dto)).thenReturn(plato); // Mock the mapper to return a valid Plato object
        //act
        platoHandler.crearPlato(dto);
        //assert
        assertThat(plato.isActivo()).isTrue();
        verify(platoServicePort, times(1)).crearPlato(plato);
    }
    //Test happy path: Actualizar plato con datos válidos
    @Test
    public void testActualizarPlato_conDatosValidos_Exito() {
        // arrange
        Long idPlato = 1L;
        PlatoUpdateRequestDto dto = new PlatoUpdateRequestDto(
                15,
                "Nueva descripción"
        );
        Plato platoActualizar = new Plato();
        platoActualizar.setPrecio(15);
        platoActualizar.setDescripcion("Nueva Descripción");

        Plato actualizado = new Plato();
        actualizado.setId(1L);
        actualizado.setPrecio(15);
        actualizado.setDescripcion("Nueva descripción");

        PlatoResponseDto responseDto = new PlatoResponseDto();
        responseDto.setId(1L);
        responseDto.setPrecio(15);
        responseDto.setDescripcion("Nueva descripción");

        when(platoUpdateRequestMapper.toDomain(dto)).thenReturn(platoActualizar);
        when(platoServicePort.actualizarPlato(platoActualizar, idPlato)).thenReturn(actualizado);
        when(platoResponseMapper.toResponse(actualizado)).thenReturn(responseDto);

        // act
        PlatoResponseDto result = platoHandler.actualizarPlato(dto, idPlato);

        // assert
        assertThat(result).isEqualTo(responseDto);
        verify(platoUpdateRequestMapper,times(1)).toDomain(dto);
        verify(platoServicePort,times(1)).actualizarPlato(platoActualizar, idPlato);
        verify(platoResponseMapper,times(1)).toResponse(actualizado);
    }

    @Test
    public void testObtenerPlatoPorId_conIdValido_Exito() {
        // arrange
        Long idPlato = 1L;
        Plato plato = new Plato(
                idPlato,
                "Limonada",
                10,
                "Descripción del plato",
                "https://example.com/image.jpg",
                Categoria.BEBIDA,
                true,
                1L
        );
        PlatoResponseDto responseDto = new PlatoResponseDto(
                idPlato,
                "Limonada",
                10,
                "Descripción del plato",
                "https://example.com/image.jpg",
                Categoria.BEBIDA,
                true,
                1L

        );
        when(platoServicePort.obtenerPlatoPorId(idPlato)).thenReturn(plato);
        when(platoResponseMapper.toResponse(plato)).thenReturn(responseDto);

        // act
        PlatoResponseDto result = platoHandler.obtenerPlatoPorId(idPlato);

        // assert
        assertThat(result).isEqualTo(responseDto);
        verify(platoServicePort).obtenerPlatoPorId(idPlato);
        verify(platoResponseMapper).toResponse(plato);
    }
    @Test
    public void testListarPlatosPorRestaurante_conDatosValidos_retornaListaDto() {
        // Arrange
        Long restauranteId = 1L;
        int page = 0;
        int size = 10;
        String categoria = "principal";

        Plato plato = new Plato(
                1L,
                "Arroz Chaufa",
                15.5f,
                "Arroz con pollo",
                "http://img.com/arroz.jpg",
                Categoria.PLATO_PRINCIPAL,
                true,
                restauranteId
        );

        PlatoResponseDto dto = new PlatoResponseDto(
                1L,
                "Arroz Chaufa",
                15.5f,
                "Arroz con pollo",
                "http://img.com/arroz.jpg",
                Categoria.PLATO_PRINCIPAL,
                true,
                restauranteId
        );

        List<Plato> platos = List.of(plato);
        List<PlatoResponseDto> expectedDtos = List.of(dto);

        when(platoServicePort.listarPlatosPorRestaurante(restauranteId, page, size, categoria))
                .thenReturn(platos);
        when(platoResponseMapper.toResponseList(platos)).thenReturn(expectedDtos);

        // Act
        List<PlatoResponseDto> resultado = platoHandler.listarPlatosPorRestaurante(restauranteId, page, size, categoria);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Arroz Chaufa", resultado.get(0).getNombre());
        assertEquals(Categoria.PLATO_PRINCIPAL, resultado.get(0).getCategoria());
        verify(platoServicePort).listarPlatosPorRestaurante(restauranteId, page, size, categoria);
        verify(platoResponseMapper).toResponseList(platos);
    }
}