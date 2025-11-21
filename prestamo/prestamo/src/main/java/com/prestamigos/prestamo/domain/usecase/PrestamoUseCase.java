package com.prestamigos.prestamo.domain.usecase;

import com.prestamigos.prestamo.domain.model.Prestamo;
import com.prestamigos.prestamo.domain.model.gateway.ClienteGateway;
import com.prestamigos.prestamo.domain.model.gateway.PrestamoGateway;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class PrestamoUseCase {

    private final PrestamoGateway prestamoGateway;
    private final ClienteGateway clienteGateway;


    public Prestamo guardarPrestamo(Prestamo prestamo) {

        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no puede ser nulo.");
        }

        if (prestamo.getProductoNombre() == null || prestamo.getProductoNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (prestamo.getMontoProducto() == null || prestamo.getMontoProducto().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del producto debe ser mayor que 0.");
        }

        if (prestamo.getMontoPrestamo() == null || prestamo.getMontoPrestamo().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del préstamo debe ser mayor que 0.");
        }

        if (prestamo.getNumCuotas() == null || prestamo.getNumCuotas() <= 0) {
            throw new IllegalArgumentException("El número de cuotas debe ser mayor que 0.");
        }

        if (prestamo.getPeriodoTipo() == null || prestamo.getPeriodoTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar el tipo de periodo (ej: semanal, quincenal, mensual).");
        }

        if (prestamo.getCuotaValor() == null || prestamo.getCuotaValor().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El valor de la cuota debe ser mayor que 0.");
        }

        if (prestamo.getClienteId() == null) {
            throw new IllegalArgumentException("Debe enviar el ClienteId del cliente.");
        }

        boolean clienteExiste = clienteGateway.existeCliente(prestamo.getClienteId());
        if (!clienteExiste) {
            throw new IllegalArgumentException(
                    "El cliente con ID " + prestamo.getClienteId() + " no existe."
            );
        }

        prestamo.setEstado("ACTIVO");
        prestamo.setFechaInicio(LocalDate.now());
        prestamo.setFechaFinal(null);

        return prestamoGateway.guardarPrestamo(prestamo);
    }


    public Prestamo buscarPorId(Long id) {
        return prestamoGateway.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado."));
    }


    public List<Prestamo> listarTodos() {
        return prestamoGateway.listarTodos();
    }



    public List<Prestamo> listarPorVendedorId(Long vendedorId) {

        List<Prestamo> prestamos = prestamoGateway.listarPorVendedorId(vendedorId);

        if (prestamos == null || prestamos.isEmpty()) {
            throw new IllegalArgumentException(
                    "El vendedor con ID " + vendedorId + " no existe o no tiene préstamos registrados."
            );
        }

        return prestamos;
    }



    public Prestamo actualizarPrestamo(Prestamo prestamo) {

        if (prestamo.getId() == null) {
            throw new IllegalArgumentException("Debe enviar el ID del préstamo.");
        }

        return prestamoGateway.actualizarPrestamo(prestamo);
    }


    public String eliminarPrestamo(Long id) {
        try {
            prestamoGateway.eliminarPrestamo(id);
            return "Préstamo eliminado correctamente con ID: " + id;
        } catch (Exception error) {
            throw new IllegalArgumentException("Error al eliminar préstamo: " + error.getMessage());
        }
    }
}

