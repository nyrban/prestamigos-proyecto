package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Vendedor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendedorDataJpaRepository extends JpaRepository<VendedorData, Long> {

    Optional<VendedorData> findByEmail(String email);

    Optional<VendedorData> findByTokenConfirmacion(String token);

}
