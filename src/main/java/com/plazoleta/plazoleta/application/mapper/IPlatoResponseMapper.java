package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPlatoResponseMapper {
    Plato toDomain(PlatoResponseDto platoResponseDto);
    PlatoResponseDto toResponse(Plato plato);
    List<PlatoResponseDto> toResponseList(List<Plato> platos);
}
