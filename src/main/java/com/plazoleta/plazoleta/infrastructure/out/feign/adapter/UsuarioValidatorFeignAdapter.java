package com.plazoleta.plazoleta.infrastructure.out.feign.adapter;

import com.plazoleta.plazoleta.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.plazoleta.domain.spi.IUsuarioValidatorPort;
import com.plazoleta.plazoleta.infrastructure.out.feign.client.IUsuarioFeignClient;
import com.plazoleta.plazoleta.infrastructure.out.feign.dto.PropietarioResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
public class UsuarioValidatorFeignAdapter implements IUsuarioValidatorPort {

    private final IUsuarioFeignClient client;

    @Override
    public boolean validarUsuario(Long idUsuario) {
        try{
            PropietarioResponseDto dto = client.buscarPropietarioPorId(idUsuario);
            return dto != null && dto.getRol() != null && dto.getRol().name().equals("PROPIETARIO");
        } catch (FeignException.NotFound e){
            return false;
        }
    }

}
