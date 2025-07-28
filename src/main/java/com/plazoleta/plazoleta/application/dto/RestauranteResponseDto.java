package com.plazoleta.plazoleta.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteResponseDto {
    private Long id;
    private String nombre;
    private Long nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long usuarioId;
}
