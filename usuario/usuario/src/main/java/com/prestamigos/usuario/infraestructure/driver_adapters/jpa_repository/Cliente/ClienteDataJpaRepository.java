package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteDataJpaRepository extends JpaRepository<ClienteData, Long> {

    Optional<ClienteData> findByEmail(String email);

    Optional<ClienteData> findByTokenConfirmacion(String token);
}