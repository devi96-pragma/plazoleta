package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.EstadoPedido;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;

import java.util.List;
import java.util.Optional;

public interface IPedidoPersistancePort {
    Pedido crearPedido(Pedido pedido);
    List<Pedido> buscarPedidosClienteEnProceso(Long idUsuario);
    List<Pedido> buscarPedidosPorEstado(Long idUsuario, Long idRestaurante, EstadoPedido estado, int page, int size);
    Optional<Pedido> buscarPedidoPorId(Long idPedido);
    void actualizarPedido(Pedido pedido);
}
