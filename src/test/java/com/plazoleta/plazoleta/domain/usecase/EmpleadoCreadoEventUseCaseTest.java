package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.model.EmpleadoCreadoEvento;
import com.plazoleta.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmpleadoCreadoEventUseCaseTest {

    @Mock
    private IEmpleadoRestaurantePersistencePort persistencePort;

    @InjectMocks
    private EmpleadoCreadoEventUseCase useCase;
    //Happy path: guardarEmpleado del evento, verificar que ese ejecuta el metodo.
    @Test
    void manejarEmpleadoCreado_guardaEmpleadoRestaurante() {
        // Arrange
        EmpleadoCreadoEvento evento = new EmpleadoCreadoEvento(1L, 2L);

        // Act
        useCase.manejarEmpleadoCreado(evento);

        // Assert
        verify(persistencePort).guardarEmpleadoRestaurante(1L, 2L);
    }
}
