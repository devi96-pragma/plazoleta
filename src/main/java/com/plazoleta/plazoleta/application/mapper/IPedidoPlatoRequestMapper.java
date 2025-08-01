package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PedidoPlatoRequestDto;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPedidoPlatoRequestMapper {
    PedidoPlato toDomain(PedidoPlatoRequestDto dto);
    List<PedidoPlato> toDomainList(List<PedidoPlatoRequestDto> listDto);
}
