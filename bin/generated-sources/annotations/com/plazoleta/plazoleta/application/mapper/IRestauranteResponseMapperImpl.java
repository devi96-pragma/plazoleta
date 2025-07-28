package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T13:44:28-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class IRestauranteResponseMapperImpl implements IRestauranteResponseMapper {

    @Override
    public RestauranteResponseDto toResponse(Restaurante restaurante) {
        if ( restaurante == null ) {
            return null;
        }

        RestauranteResponseDto restauranteResponseDto = new RestauranteResponseDto();

        restauranteResponseDto.setDireccion( restaurante.getDireccion() );
        restauranteResponseDto.setId( restaurante.getId() );
        restauranteResponseDto.setNit( restaurante.getNit() );
        restauranteResponseDto.setNombre( restaurante.getNombre() );
        restauranteResponseDto.setTelefono( restaurante.getTelefono() );
        restauranteResponseDto.setUrlLogo( restaurante.getUrlLogo() );
        restauranteResponseDto.setUsuarioId( restaurante.getUsuarioId() );

        return restauranteResponseDto;
    }
}
