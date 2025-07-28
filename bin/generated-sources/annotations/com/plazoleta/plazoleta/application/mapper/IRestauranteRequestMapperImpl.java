package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T13:44:28-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class IRestauranteRequestMapperImpl implements IRestauranteRequestMapper {

    @Override
    public Restaurante toDomain(RestauranteRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Restaurante restaurante = new Restaurante();

        restaurante.setUsuarioId( dto.getUsuarioId() );
        restaurante.setNombre( dto.getNombre() );
        restaurante.setNit( dto.getNit() );
        restaurante.setDireccion( dto.getDireccion() );
        restaurante.setTelefono( dto.getTelefono() );
        restaurante.setUrlLogo( dto.getUrlLogo() );

        return restaurante;
    }
}
