package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPlatoCreateRequestMapper {
    PlatoCreateRequestDto toRequest(Plato plato);
    Plato toDomain(PlatoCreateRequestDto platoCreateRequestDto);
}
