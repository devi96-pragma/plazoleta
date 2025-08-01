package com.plazoleta.plazoleta.infrastructure.input.rest;

import com.plazoleta.plazoleta.application.dto.RestauranteListaResponseDto;
import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;
import com.plazoleta.plazoleta.application.handler.IRestauranteHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.plazoleta.plazoleta.infrastructure.constantes.Constantes.Paginacion.PAGINA_DEFECTO;
import static com.plazoleta.plazoleta.infrastructure.constantes.Constantes.Paginacion.TAMANIO_DEFECTO;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final IRestauranteHandler restauranteHandler;

    @Operation(summary = "Crear un nuevo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Restaurante creado exitosamente",
                    content = @Content(mediaType = "application/json")),
    })
    @PostMapping()
    public ResponseEntity<Void> crearRestaurante(@RequestBody @Valid RestauranteRequestDto request){
        restauranteHandler.crearRestaurante(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "Buscar restaurante por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Restaurante encontrado correctamente",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{idRestaurante}")
    public ResponseEntity<RestauranteResponseDto> buscarRestaurantePorId(@PathVariable Long idRestaurante) {
        RestauranteResponseDto restaurante = restauranteHandler.findRestauranteById(idRestaurante);
        return ResponseEntity.ok(restaurante);
    }


    @Operation(summary = "Listar restaurantes paginados por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes obtenida correctamente",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<RestauranteListaResponseDto>> listarRestaurantes(
            @RequestParam(defaultValue = PAGINA_DEFECTO) int page,
            @RequestParam(defaultValue = TAMANIO_DEFECTO) int size
    ) {
        List<RestauranteListaResponseDto> restaurantes = restauranteHandler
                .listarRestaurantesOrdenadosPorNombre(page, size);
        return ResponseEntity.ok(restaurantes);
    }
}
