package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IEmpleadoEventHandlerServicePort;
import com.plazoleta.plazoleta.domain.model.EmpleadoCreadoEvento;
import com.plazoleta.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;

public class EmpleadoCreadoEventUseCase implements IEmpleadoEventHandlerServicePort {
    private final IEmpleadoRestaurantePersistencePort empleadoRestaurantePersistencePort;

    public EmpleadoCreadoEventUseCase(IEmpleadoRestaurantePersistencePort empleadoRestaurantePersistencePort) {
        this.empleadoRestaurantePersistencePort = empleadoRestaurantePersistencePort;
    }

    @Override
    public void manejarEmpleadoCreado(EmpleadoCreadoEvento evento) {
        empleadoRestaurantePersistencePort.guardarEmpleadoRestaurante(evento.getIdUsuario(), evento.getIdRestaurante());
    }
}
