package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPlatoUpdateRequestMapper {
    PlatoUpdateRequestDto toRequest(Plato plato);
    Plato toDomain(PlatoUpdateRequestDto platoUpdateRequestDto);
}
