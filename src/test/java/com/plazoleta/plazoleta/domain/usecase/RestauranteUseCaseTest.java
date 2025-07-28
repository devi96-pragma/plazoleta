package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioValidatorPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestauranteUseCaseTest {
    @Mock
    private IRestaurantePersistencePort restaurantePersistencePort;
    @Mock
    private IUsuarioValidatorPort usuarioValidatorPort;
    @InjectMocks
    private RestauranteUseCase restauranteUseCase;
    // Happy path: Crear restaurante con usuario válido
    @Test
    public void testCrearRestaurante_UsuarioValido_RestauranteCreado() {
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setUsuarioId(1L);
        when(usuarioValidatorPort.validarUsuario(1L)).thenReturn(true);

        restauranteUseCase.crearRestaurante(restaurante);
        verify(restaurantePersistencePort).saveRestaurante(restaurante);
    }
    // Negative path: Crear restaurante con usuario inválido
    @Test
    void testCrearRestaurante_UsuarioNoEsPropietario_LanzaExcepcion(){
        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setUsuarioId(2L);
        when(usuarioValidatorPort.validarUsuario(2L)).thenReturn(false);

        // Act & Assert
        assertThrows(UsuarioNoEsPropietarioException.class, () -> {
            restauranteUseCase.crearRestaurante(restaurante);
        });
        verify(restaurantePersistencePort, never()).saveRestaurante(any());
    }
}
