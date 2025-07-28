package com.plazoleta.plazoleta.infrastructure.out.feign.client;

import com.plazoleta.plazoleta.infrastructure.configuration.FeignClientConfig;
import com.plazoleta.plazoleta.infrastructure.out.feign.dto.PropietarioResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "usuario-service",
        url = "${feign.usuario.url}",
        configuration = FeignClientConfig.class
)
public interface IUsuarioFeignClient {
    @GetMapping("/usuarios/{idUsuario}")
    PropietarioResponseDto buscarPropietarioPorId(@PathVariable("idUsuario") Long id);
}
