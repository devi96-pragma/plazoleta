package com.plazoleta.plazoleta.infrastructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.PlatoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IPlatoEntityMapper {
    PlatoEntity toEntity(Plato plato);
    Plato toDomain(PlatoEntity entity);
}
