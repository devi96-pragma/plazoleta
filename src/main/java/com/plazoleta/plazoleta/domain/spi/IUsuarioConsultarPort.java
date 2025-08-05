package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Usuario;

public interface IUsuarioConsultarPort {
    Usuario encontrarCliente(Long idCliente);
}
