package com.prestamigos.pago.infraestructure.driver_adapters.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoDataJpaRepository extends JpaRepository<PagoData, Long> {

    List<PagoData> findByClienteId(Long clienteId);

    List<PagoData> findByPrestamoId(Long prestamoId);

    List<PagoData> findByFecha(LocalDate fecha);
}