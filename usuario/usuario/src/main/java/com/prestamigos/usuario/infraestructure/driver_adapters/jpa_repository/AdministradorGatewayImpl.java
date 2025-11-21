package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository;


import com.prestamigos.usuario.domain.model.Administrador;
import com.prestamigos.usuario.domain.model.gateway.AdministradorGateway;
import com.prestamigos.usuario.infraestructure.mapper.AdministradorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AdministradorGatewayImpl implements AdministradorGateway {

    private final AdministradorMapper administradorMapper;
    private final AdministradorDataJpaRepository repository;

    @Override
    public Administrador guardarAdministrador(Administrador administrador) {
        AdministradorData data = administradorMapper.toData(administrador);
        AdministradorData guardado = repository.save(data);
        return administradorMapper.toAdministrador(guardado);
    }

    @Override
    public List<Administrador> buscarTodos() {
        return repository.findAll().stream()
                .map(administradorMapper::toAdministrador)
                .collect(Collectors.toList());
    }

    @Override
    public Administrador buscarPorId(Long id) {
        return repository.findById(id)
                .map(administradorMapper::toAdministrador)
                .orElseThrow(() ->
                        new IllegalArgumentException("Administrador no encontrado con ID: " + id)
                );
    }

    @Override
    public Administrador buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(administradorMapper::toAdministrador)
                .orElseThrow(() ->
                        new IllegalArgumentException("Administrador no encontrado con correo: " + email)
                );
    }

    @Override
    public Administrador actualizarAdministrador(Administrador administrador) {
        if (administrador.getId() == null)
            throw new IllegalArgumentException("El ID del administrador es obligatorio para actualizar.");

        if (!repository.existsById(administrador.getId()))
            throw new IllegalArgumentException("No existe un administrador con ID: " + administrador.getId());

        AdministradorData data = administradorMapper.toData(administrador);
        AdministradorData actualizado = repository.save(data);
        return administradorMapper.toAdministrador(actualizado);
    }

    @Override
    public void eliminarAdministrador(Long id) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("No existe un administrador con ID: " + id);
        repository.deleteById(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public Administrador buscarPorToken(String token) {
        AdministradorData data = repository.findByTokenConfirmacion(token)
                .orElseThrow(() -> new RuntimeException("Administrador con token " + token + " no encontrado"));
        return administradorMapper.toAdministrador(data);
    }
}
