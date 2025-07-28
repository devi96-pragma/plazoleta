package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;

public interface IPlatoHandler {
    void crearPlato(PlatoCreateRequestDto platoRequestDto);
    PlatoResponseDto actualizarPlato(PlatoUpdateRequestDto platoRequestDto, Long idPlato);
    PlatoResponseDto obtenerPlatoPorId(Long idPlato);
}
