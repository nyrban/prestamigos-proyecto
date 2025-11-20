package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdministradorDataJpaRepository extends JpaRepository<AdministradorData, Long> {

    Optional<AdministradorData> findByEmail(String email);

    Optional<AdministradorData> findByTokenConfirmacion(String token);
}