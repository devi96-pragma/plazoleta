package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.PedidoEstadoCambiadoEvento;

public interface IPedidoCambioEstadoEventPublishPort {
    void publicarPedidoEventoCambioEstado(PedidoEstadoCambiadoEvento pedido);
}
