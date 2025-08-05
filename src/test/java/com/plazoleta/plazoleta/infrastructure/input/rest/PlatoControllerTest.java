package com.plazoleta.plazoleta.infrastructure.input.rest;

import com.plazoleta.plazoleta.application.dto.EstadoPlatoRequest;
import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;
import com.plazoleta.plazoleta.application.handler.IPlatoHandler;
import com.plazoleta.plazoleta.domain.model.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlatoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class PlatoControllerTest {
    @MockitoBean
    private IPlatoHandler platoHandler;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findPlatoById_conIdValido_debeRetornarPlato() throws Exception {
        PlatoResponseDto responseDto = new PlatoResponseDto();
        when(platoHandler.obtenerPlatoPorId(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/platos/1"))
                .andExpect(status().isOk());
        verify(platoHandler).obtenerPlatoPorId(1L);
    }

    @Test
    void actualizarPlato_debeRetornarPlatoActualizado() throws Exception {
        PlatoUpdateRequestDto updateDto = new PlatoUpdateRequestDto();
        PlatoResponseDto responseDto = new PlatoResponseDto();
        when(platoHandler.actualizarPlato(any(PlatoUpdateRequestDto.class), eq(1L))).thenReturn(responseDto);

        mockMvc.perform(patch("/platos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        verify(platoHandler).actualizarPlato(any(PlatoUpdateRequestDto.class), eq(1L));
    }

    @Test
    void crearPlato_debeRetornarCreado() throws Exception {
        doNothing().when(platoHandler).crearPlato(any(PlatoCreateRequestDto.class));

        mockMvc.perform(post("/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"nombre\": \"Ceviche Mixto\",\n" +
                                "  \"precio\": 25.50,\n" +
                                "  \"descripcion\": \"Ceviche de pescado y mariscos con camote y cancha\",\n" +
                                "  \"urlImagen\": \"https://example.com/imagenes/ceviche.jpg\",\n" +
                                "  \"categoria\": \"PLATO_PRINCIPAL\",\n" +
                                "  \"activo\": true,\n" +
                                "  \"idRestaurante\": 1\n" +
                                "}"))
                .andExpect(status().isCreated());
        verify(platoHandler).crearPlato(any(PlatoCreateRequestDto.class));
    }

    @Test
    void actualizarEstado_debeRetornarPlatoActualizado() throws Exception {
        EstadoPlatoRequest estadoRequest = new EstadoPlatoRequest();
        PlatoResponseDto responseDto = new PlatoResponseDto();
        when(platoHandler.habilitarDeshabilitarPlato(eq(1L), any(EstadoPlatoRequest.class))).thenReturn(responseDto);

        mockMvc.perform(patch("/platos/1/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        verify(platoHandler).habilitarDeshabilitarPlato(eq(1L), any(EstadoPlatoRequest.class));
    }
    @Test
    void testListarPlatosPorRestaurante_retornaListaDePlatos() throws Exception {
        // Arrange
        Long restauranteId = 1L;
        int page = 0;
        int size = 10;
        String categoria = "principal";

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

        List<PlatoResponseDto> platosMock = List.of(dto);

        when(platoHandler.listarPlatosPorRestaurante(restauranteId, page, size, categoria))
                .thenReturn(platosMock);

        // Act & Assert
        mockMvc.perform(get("/platos/restaurante/{idRestaurante}", restauranteId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("categoria", categoria))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Arroz Chaufa"))
                .andExpect(jsonPath("$[0].categoria").value("PLATO_PRINCIPAL"));

        verify(platoHandler).listarPlatosPorRestaurante(restauranteId, page, size, categoria);
    }
}
