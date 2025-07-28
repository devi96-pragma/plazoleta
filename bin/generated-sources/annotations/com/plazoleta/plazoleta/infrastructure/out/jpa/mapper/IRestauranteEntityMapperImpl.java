package com.plazoleta.plazoleta.infrastructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.RestauranteEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T13:44:28-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class IRestauranteEntityMapperImpl implements IRestauranteEntityMapper {

    @Override
    public Restaurante toDomain(RestauranteEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Restaurante restaurante = new Restaurante();

        restaurante.setUsuarioId( entity.getUsuarioId() );
        restaurante.setNombre( entity.getNombre() );
        restaurante.setNit( entity.getNit() );
        restaurante.setDireccion( entity.getDireccion() );
        restaurante.setTelefono( entity.getTelefono() );
        restaurante.setUrlLogo( entity.getUrlLogo() );
        restaurante.setId( entity.getId() );

        return restaurante;
    }

    @Override
    public RestauranteEntity toEntity(Restaurante domain) {
        if ( domain == null ) {
            return null;
        }

        RestauranteEntity restauranteEntity = new RestauranteEntity();

        restauranteEntity.setDireccion( domain.getDireccion() );
        restauranteEntity.setId( domain.getId() );
        restauranteEntity.setNit( domain.getNit() );
        restauranteEntity.setNombre( domain.getNombre() );
        restauranteEntity.setTelefono( domain.getTelefono() );
        restauranteEntity.setUrlLogo( domain.getUrlLogo() );
        restauranteEntity.setUsuarioId( domain.getUsuarioId() );

        return restauranteEntity;
    }
}
