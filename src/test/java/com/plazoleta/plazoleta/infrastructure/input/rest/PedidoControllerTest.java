package com.plazoleta.plazoleta.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta.application.dto.PedidoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PedidoResponseDto;
import com.plazoleta.plazoleta.application.handler.IPedidoHandler;
import com.plazoleta.plazoleta.domain.model.EstadoPedido;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IPedidoHandler pedidoHandler;

    @Test
    void testCrearPedido_endpoint_retornaCreated() throws Exception {
        // Arrange
        PedidoCreateRequestDto requestDto = new PedidoCreateRequestDto();
        String jsonBody = new ObjectMapper().writeValueAsString(requestDto);

        // Act & Assert
        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated());

        verify(pedidoHandler).crearPedido(any(PedidoCreateRequestDto.class));
    }
    @Test
    void testObtenerListaPedidosPorEstado_endpoint_retornaLista() throws Exception {
        // Arrange
        int page = 0;
        int size = 10;
        EstadoPedido estado = EstadoPedido.PENDIENTE;

        PedidoResponseDto dto = new PedidoResponseDto(); // puedes construir uno si quieres validar campos
        List<PedidoResponseDto> mockResponse = List.of(dto);

        when(pedidoHandler.obtenerListaPedidoPorEstado(page, size, estado)).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/pedidos")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("estado", estado.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(pedidoHandler).obtenerListaPedidoPorEstado(page, size, estado);
    }
    //Happy path asigarme pedido
    @Test
    void testAsignarmePedido_endpoint_retornaNoContent() throws Exception {
        Long idPedido = 5L;

        mockMvc.perform(patch("/pedidos/{idPedido}", idPedido))
                .andExpect(status().isNoContent());

        verify(pedidoHandler).asignarmePedido(idPedido);
    }

    @Test
    void testNotificarPedidoListo_endpoint_retornaNoContent() throws Exception {
        Long idPedido = 6L;

        mockMvc.perform(patch("/pedidos/{idPedido}/notificar-listo", idPedido))
                .andExpect(status().isNoContent());

        verify(pedidoHandler).notificarPedidoListo(idPedido);
    }

    @Test
    void testEntregarPedido_endpoint_retornaNoContent() throws Exception {
        Long idPedido = 7L;
        String pin = "1234";

        mockMvc.perform(patch("/pedidos/{idPedido}/entregar", idPedido)
                        .param("pin", pin))
                .andExpect(status().isNoContent());

        verify(pedidoHandler).entregarPedido(idPedido, pin);
    }

    @Test
    void testEntregarPedido_endpoint_sinPin_retornaBadRequest() throws Exception {
        Long idPedido = 8L;

        mockMvc.perform(patch("/pedidos/{idPedido}/entregar", idPedido))
                .andExpect(status().isBadRequest());

        verify(pedidoHandler, never()).entregarPedido(anyLong(), anyString());
    }

    @Test
    void testCancelarPedido_endpoint_retornaNoContent() throws Exception {
        Long idPedido = 9L;

        mockMvc.perform(patch("/pedidos/{idPedido}/cancelar", idPedido))
                .andExpect(status().isNoContent());

        verify(pedidoHandler).cancelarPedido(idPedido);
    }
}
