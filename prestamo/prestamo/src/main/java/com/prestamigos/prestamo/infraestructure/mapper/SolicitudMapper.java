package com.prestamigos.prestamo.infraestructure.mapper;

import com.prestamigos.prestamo.domain.model.Solicitud;
import com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Solicitud.SolicitudData;
import org.springframework.stereotype.Component;


@Component
public class SolicitudMapper {

    public Solicitud toSolicitud(SolicitudData data) {
        if (data == null) return null;

        return new Solicitud(
                data.getId(),
                data.getTipo(),
                data.getDescripcion(),
                data.getVendedorId(),
                data.getClienteId(),
                data.getAdministradorId(),
                data.getMonto(),
                data.getMetodo(),
                data.getEstado(),
                data.getFechaSolicitud(),
                data.getFechaResolucion()
        );
    }

    public SolicitudData toData(Solicitud domain) {
        if (domain == null) return null;

        return new SolicitudData(
                domain.getId(),
                domain.getTipo(),
                domain.getDescripcion(),
                domain.getVendedorId(),
                domain.getClienteId(),
                domain.getAdministradorId(),
                domain.getMonto(),
                domain.getMetodo(),
                domain.getEstado(),
                domain.getFechaSolicitud(),
                domain.getFechaResolucion()
        );
    }
}
