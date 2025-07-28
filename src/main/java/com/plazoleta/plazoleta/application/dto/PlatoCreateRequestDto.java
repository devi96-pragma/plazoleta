package com.plazoleta.plazoleta.application.dto;

import com.plazoleta.plazoleta.domain.model.Categoria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlatoCreateRequestDto {
    @NotBlank(message = "El nombre del plato es obligatorio")
    private String nombre;
    @Min(value = 1, message = "El precio del plato debe ser mayor a 0")
    private float precio;
    @NotBlank(message = "La descripción del plato es obligatoria")
    private String descripcion;
    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "La URL de la imagen debe ser válida"
    )
    private String urlImagen;
    @NotNull(message = "La categoría es obligatoria")
    private Categoria categoria;
    @NotNull(message = "El ID del restaurante es obligatorio")
    private Long idRestaurante;
}
