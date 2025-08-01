package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.EstadoPedido;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import java.util.List;

public interface IPedidoServicePort {
    void crearPedido(Pedido pedido);
    List<Pedido> buscarPedidosClienteEnProceso(Long idUsuario);
    List<Pedido> buscarPedidosPorEstado(int page, int size, EstadoPedido estado);
}
