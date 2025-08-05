package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.PedidoListoEvento;

public interface IPedidoEventPublishPort {
    void notificarPedidoCliente(PedidoListoEvento pedido);
}
