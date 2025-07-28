package com.plazoleta.plazoleta.infrastructure.input.rest;

import com.plazoleta.plazoleta.application.dto.RestauranteRequestDto;
import com.plazoleta.plazoleta.application.dto.RestauranteResponseDto;
import com.plazoleta.plazoleta.application.handler.IRestauranteHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {
    private final IRestauranteHandler restauranteHandler;

    @PostMapping()
    public ResponseEntity<Void> crearRestaurante(@RequestBody @Valid RestauranteRequestDto request){
        restauranteHandler.crearRestaurante(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{idRestaurante}")
    public ResponseEntity<RestauranteResponseDto> buscarRestaurantePorId(@PathVariable Long idRestaurante) {
        RestauranteResponseDto restaurante = restauranteHandler.findRestauranteById(idRestaurante);
        return ResponseEntity.ok(restaurante);
    }
}
