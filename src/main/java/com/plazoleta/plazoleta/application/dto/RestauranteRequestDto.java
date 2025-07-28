package com.plazoleta.plazoleta.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RestauranteRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(
            regexp = "^(?!\\d+$)[a-zA-Z0-9áéíóúÁÉÍÓÚñÑüÜ\\s]+$",
            message = "El nombre no puede contener solo números y debe ser alfanumérico"
    )
    private String nombre;
    @Min(value = 1000000, message = "El NIT debe tener al menos 7 dígitos")
    @NotNull(message = "El NIT es obligatorio")
    private Long nit;
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(
            regexp = "^\\+\\d{11,13}$",
            message = "El teléfono debe empezar con '+' seguido de entre 11 y 13 dígitos"
    )
    private String telefono;
    @NotBlank(message = "La URL del logo no puede estar vacía")
    private String urlLogo;
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long usuarioId;
}
