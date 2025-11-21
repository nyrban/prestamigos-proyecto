package com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Solicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudDataJpaRepository extends JpaRepository<SolicitudData, Long> {

    // Buscar todas las solicitudes de un cliente
    List<SolicitudData> findByClienteId(Long clienteId);

    // Buscar todas las solicitudes con estado PENDIENTE
    List<SolicitudData> findByEstado(String estado);
}
