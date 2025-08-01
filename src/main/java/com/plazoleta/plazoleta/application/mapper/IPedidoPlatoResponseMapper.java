package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PedidoPlatoResponseDto;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPedidoPlatoResponseMapper {
    PedidoPlatoResponseDto toResponse(PedidoPlato pedidoPlato);
    List<PedidoPlatoResponseDto> toResponseList(List<PedidoPlato> pedidoPlatos);
}
