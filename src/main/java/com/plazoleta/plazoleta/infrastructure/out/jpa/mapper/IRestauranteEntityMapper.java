package com.plazoleta.plazoleta.infrastructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.RestauranteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IRestauranteEntityMapper {
    Restaurante toDomain(RestauranteEntity entity);
    RestauranteEntity toEntity(Restaurante domain);
}
