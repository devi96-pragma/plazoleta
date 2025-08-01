package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.RestauranteListaResponseDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IRestauranteResponseMapper {
        RestauranteResponseDto toResponse(Restaurante restaurante);
        List<RestauranteListaResponseDto> toResponseList(List<Restaurante> restaurantes);
}
