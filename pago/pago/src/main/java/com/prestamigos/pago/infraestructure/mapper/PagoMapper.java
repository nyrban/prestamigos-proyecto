package com.prestamigos.pago.infraestructure.mapper;

import com.prestamigos.pago.domain.model.Pago;
import com.prestamigos.pago.infraestructure.driver_adapters.jpa_repository.PagoData;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public  Pago toPago(PagoData data) {
        if (data == null) return null;

        return new Pago(
                data.getId(),
                data.getPrestamoId(),
                data.getClienteId(),
                data.getMetodo(),
                data.getTipoPago(),
                data.getMonto(),
                data.getFecha()
        );
    }

    public PagoData toData(Pago pago) {
        if (pago == null) return null;

        return new PagoData(
                pago.getId(),
                pago.getPrestamoId(),
                pago.getClienteId(),
                pago.getMetodo(),
                pago.getTipoPago(),
                pago.getMonto(),
                pago.getFecha()
        );
    }
}
