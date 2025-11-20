package com.prestamigos.usuario.domain.model.gateway;

import com.prestamigos.usuario.domain.model.Vendedor;
import java.util.List;

public interface VendedorGateway {

    Vendedor guardarVendedor(Vendedor vendedor);
    List<Vendedor> buscarTodos();
    Vendedor buscarPorId(Long id);
    Vendedor buscarPorEmail(String email);
    Vendedor actualizarVendedor(Vendedor vendedor);
    void eliminarVendedor(Long id);
    boolean existePorEmail(String email);
    Vendedor buscarPorToken(String token);
    //para el cliente
    boolean existeVendedor(Long vendedorId);

}
