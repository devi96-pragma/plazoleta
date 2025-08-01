package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.EmpleadoCreadoEvento;

public interface IEmpleadoEventHandlerServicePort {
    void manejarEmpleadoCreado(EmpleadoCreadoEvento evento);
}
