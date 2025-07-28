package com.plazoleta.plazoleta.infrastructure.input.rest;

import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;
import com.plazoleta.plazoleta.application.handler.IPlatoHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
@RequiredArgsConstructor
public class PlatoController {
    private final IPlatoHandler platoHandler;
    @Operation(summary = "Obtener un plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato encontrado",
                    content = @Content(schema = @Schema(implementation = PlatoResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)
    })
    @GetMapping("/{idPlato}")
    public ResponseEntity<PlatoResponseDto> findPlatoById(@PathVariable Long idPlato) {
        PlatoResponseDto plato = platoHandler.obtenerPlatoPorId(idPlato);
        return ResponseEntity.ok(plato);
    }
    @Operation(summary = "Actualizar un plato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = PlatoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)
    })
    @PatchMapping("/{idPlato}")
    public ResponseEntity<PlatoResponseDto> actualizarPlato(@PathVariable Long idPlato,@RequestBody @Valid PlatoUpdateRequestDto platoRequest) {
        PlatoResponseDto platoActualizado = platoHandler.actualizarPlato(platoRequest, idPlato);
        return ResponseEntity.ok(platoActualizado);
    }
    @Operation(summary = "Crear un nuevo plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> crearPlato(@RequestBody @Valid PlatoCreateRequestDto platoRequest) {
        platoHandler.crearPlato(platoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
