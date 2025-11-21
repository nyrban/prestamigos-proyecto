package com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Prestamo;

import com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Prestamo.PrestamoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoDataJpaRepository extends JpaRepository<PrestamoData, Long> {

    // Listar todos los préstamos hechos por un vendedor
    List<PrestamoData> findByVendedorId(Long vendedorId);

    // Listar todos los préstamos de un usuario (cliente)
    List<PrestamoData> findByclienteId(Long clienteId);
}
