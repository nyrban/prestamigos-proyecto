package com.prestamigos.pago.domain.usecase;

import com.prestamigos.pago.domain.model.Pago;
import com.prestamigos.pago.domain.model.gateway.ClienteGateway;
import com.prestamigos.pago.domain.model.gateway.PagoGateway;
import com.prestamigos.pago.domain.model.gateway.PrestamoGateway;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PagoUseCase {

    private final PagoGateway pagoGateway;
    private final ClienteGateway clienteGateway;
    private final PrestamoGateway prestamoGateway;


    public Object guardarPago(Pago pago) {

        if (!prestamoGateway.existePrestamo(pago.getPrestamoId())) {
            return "El préstamo con ID " + pago.getPrestamoId() + " no existe.";
        }

        if (!clienteGateway.existeCliente(pago.getClienteId())) {
            return "El cliente con ID " + pago.getClienteId() + " no existe.";
        }

        if (!prestamoGateway.prestamoPerteneceAlCliente(pago.getPrestamoId(), pago.getClienteId())) {
            return "El préstamo " + pago.getPrestamoId() + " NO pertenece al cliente " + pago.getClienteId();
        }

        if (pago.getMetodo() == null || pago.getMetodo().isBlank()) {
            return "Debe enviar el método de pago (efectivo, nequi, etc).";
        }

        if (!esMetodoValido(pago.getMetodo())) {
            return "Método de pago inválido.";
        }

        if (pago.getTipoPago() == null || pago.getTipoPago().isBlank()) {
            return "Debe enviar el tipo de pago (cuota, abono capital, etc).";
        }

        if (!esTipoValido(pago.getTipoPago())) {
            return "Tipo de pago inválido.";
        }

        if (pago.getMonto() == null || pago.getMonto().doubleValue() <= 0) {
            return "El monto debe ser mayor a 0.";
        }

        pago.setFecha(LocalDate.now());

        return pagoGateway.guardarPago(pago);
    }


    private boolean esMetodoValido(String metodo) {
        return metodo.equalsIgnoreCase("efectivo")
                || metodo.equalsIgnoreCase("nequi")
                || metodo.equalsIgnoreCase("daviplata");
    }

    private boolean esTipoValido(String tipo) {
        return tipo.equalsIgnoreCase("cuota")
                || tipo.equalsIgnoreCase("abono capital");
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoGateway.buscarPorId(id);
    }

    public List<Pago> listarTodos() {
        return pagoGateway.listarTodos();
    }

    public List<Pago> listarPorPrestamoId(Long prestamoId) {
        return pagoGateway.listarPorPrestamoId(prestamoId);
    }

    public List<Pago> listarPorClienteId(Long clienteId) {
        return pagoGateway.listarPorClienteId(clienteId);
    }

    public List<Pago> listarPorFecha(LocalDate fecha) {
        return pagoGateway.listarPorFecha(fecha);
    }

    public Object actualizarPago(Pago pago) {

        Optional<Pago> existente = pagoGateway.buscarPorId(pago.getId());
        if (existente.isEmpty()) {
            return "El pago con ID " + pago.getId() + " no existe.";
        }

        if (!prestamoGateway.existePrestamo(pago.getPrestamoId())) {
            return "El préstamo con ID " + pago.getPrestamoId() + " no existe.";
        }

        if (!clienteGateway.existeCliente(pago.getClienteId())) {
            return "El cliente con ID " + pago.getClienteId() + " no existe.";
        }

        if (!prestamoGateway.prestamoPerteneceAlCliente(pago.getPrestamoId(), pago.getClienteId())) {
            return "El préstamo " + pago.getPrestamoId() + " NO pertenece al cliente " + pago.getClienteId();
        }

        if (pago.getMetodo() == null || pago.getMetodo().isBlank()) {
            return "Debe enviar el método de pago.";
        }
        if (!esMetodoValido(pago.getMetodo())) {
            return "Método de pago inválido.";
        }

        if (pago.getTipoPago() == null || pago.getTipoPago().isBlank()) {
            return "Debe enviar el tipo de pago.";
        }
        if (!esTipoValido(pago.getTipoPago())) {
            return "Tipo de pago inválido.";
        }

        if (pago.getMonto() == null || pago.getMonto().doubleValue() <= 0) {
            return "El monto debe ser mayor a 0.";
        }

        pago.setFecha(LocalDate.now());

        return pagoGateway.guardarPago(pago);
    }



    public Object eliminarPago(Long id) {
        Optional<Pago> pago = pagoGateway.buscarPorId(id);
        if (pago.isEmpty()) {
            return "El pago con ID " + id + " no existe.";
        }
        pagoGateway.eliminarPago(id);
        return "Pago eliminado correctamente";
    }
}