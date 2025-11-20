package com.prestamigos.usuario.domain.model.gateway;

import com.prestamigos.usuario.domain.model.Administrador;

import java.util.List;


public interface AdministradorGateway {

    Administrador guardarAdministrador(Administrador administrador);
    List<Administrador> buscarTodos();
    Administrador buscarPorId(Long id);
    Administrador buscarPorEmail(String email);
    Administrador actualizarAdministrador(Administrador administrador);
    void eliminarAdministrador(Long id);
    boolean existePorEmail(String email);
    Administrador buscarPorToken(String token);
}