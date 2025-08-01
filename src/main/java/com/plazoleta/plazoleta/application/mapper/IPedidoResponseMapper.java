package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PedidoPlatoRequestDto;
import com.plazoleta.plazoleta.application.dto.PedidoPlatoResponseDto;
import com.plazoleta.plazoleta.application.dto.PedidoResponseDto;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = IPedidoPlatoResponseMapper.class,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPedidoResponseMapper {
    List<PedidoResponseDto> toResponseList(List<Pedido> pedidos);
}
