package com.prestamigos.prestamo.domain.usecase;

import com.prestamigos.prestamo.domain.model.Solicitud;
import com.prestamigos.prestamo.domain.model.gateway.ClienteGateway;
import com.prestamigos.prestamo.domain.model.gateway.SolicitudGateway;
import com.prestamigos.prestamo.domain.model.gateway.AdministradorGateway;

import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class SolicitudUseCase {

    private final SolicitudGateway solicitudGateway;
    private final ClienteGateway clienteGateway;
    private final AdministradorGateway administradorGateway;


    public Solicitud crearSolicitud(Solicitud solicitud) {

        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula.");
        }

        if (solicitud.getTipo() == null || solicitud.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de solicitud no puede estar vacío.");
        }

        if (solicitud.getDescripcion() == null || solicitud.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        if (solicitud.getMonto() == null) {
            throw new IllegalArgumentException("Debe enviar un monto.");
        }

        if (solicitud.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0.");
        }

        if (solicitud.getMonto() > 5_000_000) {
            throw new IllegalArgumentException("El monto no puede superar los 5 millones.");
        }

        if (solicitud.getMetodo() == null || solicitud.getMetodo().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un método de pago.");
        }

        String metodo = solicitud.getMetodo().trim().toLowerCase();

        List<String> metodosPermitidos = List.of("efectivo", "transferencia", "pse", "nequi", "punto fisico","");

        if (!metodosPermitidos.contains(metodo)) {
            throw new IllegalArgumentException(
                    "Método de pago inválido. Solo se permite: efectivo, transferencia, pse, nequi, punto fisico.");
        }

        solicitud.setMetodo(metodo); // normalizar

        if (solicitud.getClienteId() == null) {
            throw new IllegalArgumentException("Debe enviar el clienteId del cliente.");
        }

        boolean clienteExiste = clienteGateway.existeCliente(solicitud.getClienteId());
        if (!clienteExiste) {
            // ESTO evita el error 500 — IllegalArgumentException tu controller la captura y responde 200.
            throw new IllegalArgumentException(
                    "El cliente con ID " + solicitud.getClienteId() + " no existe."
            );
        }

        solicitud.setEstado("PENDIENTE");
        solicitud.setFechaSolicitud(LocalDate.now());
        solicitud.setAdministradorId(null);
        solicitud.setFechaResolucion(null);

        return solicitudGateway.guardarSolicitud(solicitud);
    }


    public Solicitud buscarPorId(Long id) {
        return solicitudGateway.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada."));
    }



    public List<Solicitud> buscarPorCliente(Long usuarioId) {
        return solicitudGateway.buscarPorCliente(usuarioId);
    }


    public List<Solicitud> buscarTodasPendientes() {
        return solicitudGateway.buscarTodasPendientes();
    }


    public Solicitud cambiarEstado(Long solicitudId, String nuevoEstado, Long administradorId) {

        if (!nuevoEstado.equalsIgnoreCase("aprobado") &&
                !nuevoEstado.equalsIgnoreCase("rechazado")) {
            throw new IllegalArgumentException("Estado inválido: " + nuevoEstado);
        }

        // Validación del administrador
        if (administradorId == null) {
            throw new IllegalArgumentException("Debe enviar el administradorId.");
        }

        boolean adminExiste = administradorGateway.existeAdministrador(administradorId);
        if (!adminExiste) {
            throw new IllegalArgumentException("El administrador con ID " + administradorId + " no existe.");
        }

        return solicitudGateway.actualizarEstado(solicitudId, nuevoEstado, administradorId);
    }

    public String eliminarSolicitudPorId(Long id) {
        try {
            solicitudGateway.eliminarSolicitud(id);
            return "solicitud eliminado correctamente con ID: " + id;
        } catch (Exception error) {
            throw new IllegalArgumentException("Error al solicitud administrador: " + error.getMessage());
        }
    }
}
