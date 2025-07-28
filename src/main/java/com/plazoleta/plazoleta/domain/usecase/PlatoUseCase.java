package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.domain.exception.PlatoNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEsDelUsuarioException;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;

import java.util.Optional;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final ITokenServicePort tokenServicePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort,
                        IRestaurantePersistencePort restaurantePersistencePort
    , ITokenServicePort tokenServicePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.tokenServicePort = tokenServicePort;
    }
    //TODO: cambiar el idUsuario cuando tenga la autenticación implementada
    @Override
    public void crearPlato(Plato plato) {
        Restaurante restaurante = restaurantePersistencePort.findRestauranteById(plato.getIdRestaurante())
                .orElseThrow(() -> new RestauranteNoEncontradoException("Restaurante no encontrado"));

        if(!restaurante.getUsuarioId().equals(tokenServicePort.getUserIdFromToken())) {
            throw new RestauranteNoEsDelUsuarioException("El usuario no es propietario del restaurante");
        }
        platoPersistencePort.crearPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Plato plato, Long idPlato) {
        // Verificar si el plato existe
        Plato platoEncontrado = obtenerPlatoPorId(idPlato);
        platoEncontrado.actualizarDesde(plato);
        return platoPersistencePort.actualizarPlato(platoEncontrado);
    }

    @Override
    public Plato obtenerPlatoPorId(Long idPlato) {
        return platoPersistencePort.findPlatoById(idPlato)
                .orElseThrow(() -> new PlatoNoEncontradoException("Plato no encontrado"));
    }
}
