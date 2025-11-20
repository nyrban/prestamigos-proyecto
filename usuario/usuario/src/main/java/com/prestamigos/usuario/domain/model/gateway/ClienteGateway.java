package com.prestamigos.usuario.domain.model.gateway;

import com.prestamigos.usuario.domain.model.Cliente;
import java.util.List;

public interface ClienteGateway {

    Cliente guardarCliente(Cliente cliente);
    List<Cliente> buscarTodos();
    Cliente buscarPorId(Long id);
    Cliente buscarPorEmail(String email);
    Cliente actualizarCliente(Cliente cliente);
    void eliminarCliente(Long id);
    boolean existePorEmail(String email);
    Cliente buscarPorToken(String token);

}
