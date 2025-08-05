package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.domain.exception.CategoriaInvalidaException;
import com.plazoleta.plazoleta.domain.exception.PlatoNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.PlatoYaEnEstadoSolicitadoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEsDelUsuarioException;
import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioValidatorPort;

import java.util.List;

import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.*;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final ITokenServicePort tokenServicePort;
    private final IUsuarioValidatorPort usuarioValidatorPort;
    private final IRestauranteServicePort restauranteServicePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort
    , ITokenServicePort tokenServicePort, IUsuarioValidatorPort usuarioValidatorPort,
                        IRestauranteServicePort restauranteServicePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.tokenServicePort = tokenServicePort;
        this.usuarioValidatorPort = usuarioValidatorPort;
        this.restauranteServicePort = restauranteServicePort;
    }

    @Override
    public void crearPlato(Plato plato) {
        validarRestauranteDeUsuario(plato.getIdRestaurante());
        platoPersistencePort.crearPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Plato plato, Long idPlato) {
        // Verificar si el plato existe
        Plato platoEncontrado = obtenerPlatoPorId(idPlato);
        //verifica si el usuario es propietario del restaurante
        validarRestauranteDeUsuario(platoEncontrado.getIdRestaurante());
        platoEncontrado.actualizarDesde(plato);
        return platoPersistencePort.actualizarPlato(platoEncontrado);
    }

    @Override
    public Plato obtenerPlatoPorId(Long idPlato) {
        return platoPersistencePort.findPlatoById(idPlato)
                .orElseThrow(() -> new PlatoNoEncontradoException(PLATO_NO_ENCONTRADO));
    }
    @Override
    public Plato habilitarDeshabilitarPlato(Long idPlato, boolean estado) {
        Plato plato = obtenerPlatoPorId(idPlato);
        //validar Restaurante del plato
        validarRestauranteDeUsuario(plato.getIdRestaurante());

        if (plato.isActivo() == estado) {
            throw new PlatoYaEnEstadoSolicitadoException(PLATO_YA_EN_ESTADO_SOLICITADO);
        }
        plato.setActivo(estado);
        return platoPersistencePort.actualizarPlato(plato);
    }

    @Override
    public List<Plato> listarPlatosPorRestaurante(Long idRestaurante, int page, int size, String categoria) {
        //Valida Categoria
        Categoria categoriaEnum = convertirCategoria(categoria);
        //Validar que el restaurante existe y es de el usuario
        //validarRestauranteDeUsuario(idRestaurante);
        //Listar los platos del restaurante
        if (categoriaEnum == null) {
            return platoPersistencePort.listarPlatosPorRestaurante(idRestaurante, page, size);
        } else {
            return platoPersistencePort.listarplatosPorRestauranteYCategoria(idRestaurante, categoriaEnum, page, size);
        }
    }
    private void validarRestauranteDeUsuario(Long idRestaurante) {
        Restaurante restaurante = restauranteServicePort.findRestauranteById(idRestaurante);
        if (!restaurante.getUsuarioId().equals(tokenServicePort.getUserIdFromToken())) {
            throw new RestauranteNoEsDelUsuarioException(RESTAURANTE_NO_PERTENECE_AL_USUARIO);
        }
    }
    private Categoria convertirCategoria(String categoria) {
        if (categoria != null && !categoria.isBlank()) {
            try {
                return Categoria.valueOf(categoria.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CategoriaInvalidaException(CATEGORIA_NO_ENCONTRADA);
            }
        }
        return null;
    }
}
