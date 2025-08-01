package com.plazoleta.plazoleta.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta.application.dto.RestauranteListaResponseDto;
import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;
import com.plazoleta.plazoleta.application.handler.IRestauranteHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestauranteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class RestauranteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IRestauranteHandler restauranteHandler;

    @Test
    void crearRestaurante_debeRetornar201() throws Exception {
        RestauranteRequestDto requestDto = new RestauranteRequestDto();
        // puedes setear atributos si quieres validaciones más completas

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"nombre\": \"Test\",\n" +
                                "  \"nit\": 12345672,\n" +
                                "  \"direccion\": \"Av. Siempre Viva 742\",\n" +
                                "  \"telefono\": \"+51987654321\",\n" +
                                "  \"urlLogo\": \"https://misimagenes.com/logo.png\",\n" +
                                "  \"usuarioId\": 2\n" +
                                "  }"))
                .andExpect(status().isCreated());

        verify(restauranteHandler).crearRestaurante(any(RestauranteRequestDto.class));
    }
    @Test
    void buscarRestaurantePorId_debeRetornar200ConRestaurante() throws Exception {
        RestauranteResponseDto responseDto = new RestauranteResponseDto();
        responseDto.setNombre("Pizza Town");

        when(restauranteHandler.findRestauranteById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/restaurantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pizza Town"));

        verify(restauranteHandler).findRestauranteById(1L);
    }
    @Test
    void listarRestaurantes_debeRetornar200ConLista() throws Exception {
        RestauranteListaResponseDto dto1 = new RestauranteListaResponseDto();
        dto1.setNombre("A");

        RestauranteListaResponseDto dto2 = new RestauranteListaResponseDto();
        dto2.setNombre("B");

        List<RestauranteListaResponseDto> lista = List.of(dto1, dto2);

        when(restauranteHandler.listarRestaurantesOrdenadosPorNombre(0, 10)).thenReturn(lista);

        mockMvc.perform(get("/restaurantes?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("A"));

        verify(restauranteHandler).listarRestaurantesOrdenadosPorNombre(0, 10);
    }
}
