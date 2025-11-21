package com.prestamigos.prestamo.infraestructure.mapper;

import com.prestamigos.prestamo.domain.model.Prestamo;
import com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Prestamo.PrestamoData;
import org.springframework.stereotype.Component;

@Component
public class PrestamoMapper {

    public Prestamo toPrestamo(PrestamoData data) {
        if (data == null) return null;

        return new Prestamo(
                data.getId(),
                data.getClienteId(),
                data.getVendedorId(),
                data.getProductoNombre(),
                data.getMontoProducto(),
                data.getMontoPrestamo(),
                data.getNumCuotas(),
                data.getPeriodoTipo(),
                data.getCuotaValor(),
                data.getEstado(),
                data.getFechaInicio(),
                data.getFechaFinal()
        );
    }

    public PrestamoData toData(Prestamo domain) {
        if (domain == null) return null;

        return new PrestamoData(
                domain.getId(),
                domain.getClienteId(),
                domain.getVendedorId(),
                domain.getProductoNombre(),
                domain.getMontoProducto(),
                domain.getMontoPrestamo(),
                domain.getNumCuotas(),
                domain.getPeriodoTipo(),
                domain.getCuotaValor(),
                domain.getEstado(),
                domain.getFechaInicio(),
                domain.getFechaFinal()
        );
    }
}

