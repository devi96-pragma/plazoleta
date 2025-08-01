package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PedidoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PedidoPlatoRequestDto;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = IPedidoPlatoRequestMapper.class,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPedidoCreateRequestMapper {
    Pedido toDomain(PedidoCreateRequestDto pedidoCreateRequestDto);

    default PedidoPlato map(PedidoPlatoRequestDto dto) {
        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setCantidad(dto.getCantidad());

        Plato plato = new Plato();
        plato.setId(dto.getIdPlato());
        pedidoPlato.setIdPlato(plato.getId());

        return pedidoPlato;
    }
}
