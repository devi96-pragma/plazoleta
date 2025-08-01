package com.plazoleta.plazoleta.application.handler;


import com.plazoleta.plazoleta.application.dto.PedidoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PedidoResponseDto;
import com.plazoleta.plazoleta.domain.model.EstadoPedido;

import java.util.List;

public interface IPedidoHandler {
    void crearPedido(PedidoCreateRequestDto pedido);
    List<PedidoResponseDto> obtenerListaPedidoPorEstado(int page, int size, EstadoPedido estado);
}
