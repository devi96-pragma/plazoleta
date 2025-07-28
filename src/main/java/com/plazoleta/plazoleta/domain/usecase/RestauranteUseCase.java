package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioValidatorPort;

public class RestauranteUseCase implements IRestauranteServicePort {

    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioValidatorPort usuarioValidatorPort;
    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, IUsuarioValidatorPort usuarioValidatorPort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioValidatorPort = usuarioValidatorPort;
    }
    @Override
    public void crearRestaurante(Restaurante restaurante) {
        if(!usuarioValidatorPort.validarUsuario(restaurante.getUsuarioId())) {
            throw new UsuarioNoEsPropietarioException("El usuario no es propietario o no existe");
        }
        restaurantePersistencePort.saveRestaurante(restaurante);
    }
    @Override
    public Restaurante findRestauranteById(Long idRestaurante) {
        return restaurantePersistencePort.findRestauranteById(idRestaurante)
                .orElseThrow(() -> new RestauranteNoEncontradoException("Restaurante no encontrado"));
    }
}
