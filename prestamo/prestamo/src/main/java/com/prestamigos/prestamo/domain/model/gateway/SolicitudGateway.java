package com.prestamigos.prestamo.domain.model.gateway;


import com.prestamigos.prestamo.domain.model.Solicitud;

import java.util.List;
import java.util.Optional;

public interface SolicitudGateway {

    Solicitud guardarSolicitud(Solicitud solicitud);
    Optional<Solicitud> buscarPorId(Long id);
    List<Solicitud> buscarPorCliente(Long usuarioId);
    List<Solicitud> buscarTodasPendientes();
    Solicitud actualizarEstado(Long id, String nuevoEstado, Long administradorId);
    List<Solicitud> buscarTodas();
    void eliminarSolicitud (Long id);

}