package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioValidatorPort;

import java.util.List;

import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.RESTAURANTE_NO_ENCONTRADO;
import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.USUARIO_NO_ES_PROPIETARIO;

public class RestauranteUseCase implements IRestauranteServicePort {

    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IUsuarioValidatorPort usuarioValidatorPort;
    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, IUsuarioValidatorPort usuarioValidatorPort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.usuarioValidatorPort = usuarioValidatorPort;
    }
    @Override
    public void crearRestaurante(Restaurante restaurante) {
        if(!usuarioValidatorPort.validarUsuarioPropietario(restaurante.getUsuarioId())) {
            throw new UsuarioNoEsPropietarioException(USUARIO_NO_ES_PROPIETARIO);
        }
        restaurantePersistencePort.saveRestaurante(restaurante);
    }
    @Override
    public Restaurante findRestauranteById(Long idRestaurante) {
        return restaurantePersistencePort.findRestauranteById(idRestaurante)
                .orElseThrow(() -> new RestauranteNoEncontradoException(RESTAURANTE_NO_ENCONTRADO));
    }
    @Override
    public List<Restaurante> listarRestaurantesOrdenadosPorNombre(int page, int size) {
        return restaurantePersistencePort.listarRestaurantesOrdenadosPorNombre(page, size);
    }
}
