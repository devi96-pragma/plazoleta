package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.PedidoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PedidoResponseDto;
import com.plazoleta.plazoleta.application.mapper.IPedidoCreateRequestMapper;
import com.plazoleta.plazoleta.application.mapper.IPedidoResponseMapper;
import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.model.EstadoPedido;
import com.plazoleta.plazoleta.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoHandlerTest {

    @Mock
    private IPedidoServicePort pedidoServicePort;

    @Mock
    private IPedidoCreateRequestMapper pedidoCreateRequestMapper;

    @Mock
    private IPedidoResponseMapper pedidoResponseMapper;

    @InjectMocks
    private PedidoHandler pedidoHandler;

    @Test
    void testCrearPedido_conDatosValidos_delegaCorrectamente() {
        // Arrange
        PedidoCreateRequestDto requestDto = new PedidoCreateRequestDto();
        Pedido pedido = new Pedido();

        when(pedidoCreateRequestMapper.toDomain(requestDto)).thenReturn(pedido);

        // Act
        pedidoHandler.crearPedido(requestDto);

        // Assert
        verify(pedidoCreateRequestMapper).toDomain(requestDto);
        verify(pedidoServicePort).crearPedido(pedido);
    }
    @Test
    void testObtenerListaPedidoPorEstado_conEstadoValido_retornaListaDto() {
        // Arrange
        int page = 0;
        int size = 5;
        EstadoPedido estado = EstadoPedido.EN_PREPARACION;

        List<Pedido> pedidos = List.of(new Pedido());
        List<PedidoResponseDto> responseDtos = List.of(new PedidoResponseDto());

        when(pedidoServicePort.buscarPedidosPorEstado(page, size, estado)).thenReturn(pedidos);
        when(pedidoResponseMapper.toResponseList(pedidos)).thenReturn(responseDtos);

        // Act
        List<PedidoResponseDto> resultado = pedidoHandler.obtenerListaPedidoPorEstado(page, size, estado);

        // Assert
        assertEquals(responseDtos, resultado);
        verify(pedidoServicePort).buscarPedidosPorEstado(page, size, estado);
        verify(pedidoResponseMapper).toResponseList(pedidos);
    }

    @Test
    void testAsignarmePedido_delegatesToService() {
        Long idPedido = 42L;
        pedidoHandler.asignarmePedido(idPedido);
        verify(pedidoServicePort).asignarmePedido(idPedido);
    }

    @Test
    void testNotificarPedidoListo_delegatesToService() {
        Long idPedido = 43L;
        pedidoHandler.notificarPedidoListo(idPedido);
        verify(pedidoServicePort).notificarPedidoCliente(idPedido);
    }

    @Test
    void testEntregarPedido_delegatesToService() {
        Long idPedido = 44L;
        String pin = "9999";
        pedidoHandler.entregarPedido(idPedido, pin);
        verify(pedidoServicePort).entregarPedido(idPedido, pin);
    }

    @Test
    void testCancelarPedido_delegatesToService() {
        Long idPedido = 45L;
        pedidoHandler.cancelarPedido(idPedido);
        verify(pedidoServicePort).cancelarPedido(idPedido);
    }
}
