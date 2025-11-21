package com.prestamigos.prestamo.domain.model.gateway;

import com.prestamigos.prestamo.domain.model.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoGateway {

    Prestamo guardarPrestamo(Prestamo prestamo);

    Optional<Prestamo> buscarPorId(Long id);

    List<Prestamo> listarTodos();

    List<Prestamo> listarPorVendedorId(Long vendedorId);

    Prestamo actualizarPrestamo(Prestamo prestamo);

    void eliminarPrestamo(Long id);
}
