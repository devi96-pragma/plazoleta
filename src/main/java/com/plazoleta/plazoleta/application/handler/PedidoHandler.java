package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.PedidoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PedidoResponseDto;
import com.plazoleta.plazoleta.application.mapper.IPedidoCreateRequestMapper;
import com.plazoleta.plazoleta.application.mapper.IPedidoResponseMapper;
import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.model.EstadoPedido;
import com.plazoleta.plazoleta.domain.model.Pedido;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoHandler implements IPedidoHandler{
    private final IPedidoServicePort pedidoServicePort;
    private final IPedidoCreateRequestMapper pedidoCreateRequestMapper;
    private final IPedidoResponseMapper pedidoResponseMapper;
    @Override
    public void crearPedido(PedidoCreateRequestDto pedido) {
        pedidoServicePort.crearPedido(
                pedidoCreateRequestMapper.toDomain(pedido)
        );
    }

    @Override
    public List<PedidoResponseDto> obtenerListaPedidoPorEstado(int page, int size, EstadoPedido estado) {
        List<Pedido> pedidos = pedidoServicePort.buscarPedidosPorEstado(page,size,estado);
        return pedidoResponseMapper.toResponseList(pedidos);
    }

    @Override
    public void asignarmePedido(Long idPedido) {
        pedidoServicePort.asignarmePedido(idPedido);
    }

    @Override
    public void notificarPedidoListo(Long idPedido) {
        pedidoServicePort.notificarPedidoCliente(idPedido);
    }

    @Override
    public void entregarPedido(Long idPedido, String pin) {
        pedidoServicePort.entregarPedido(idPedido,pin);
    }

    @Override
    public void cancelarPedido(Long idPedido) {
        pedidoServicePort.cancelarPedido(idPedido);
    }
}

