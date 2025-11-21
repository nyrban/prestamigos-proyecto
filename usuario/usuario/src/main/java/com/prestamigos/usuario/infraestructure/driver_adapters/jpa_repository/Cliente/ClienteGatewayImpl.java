package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Cliente;

import com.prestamigos.usuario.domain.model.Cliente;
import com.prestamigos.usuario.domain.model.gateway.ClienteGateway;
import com.prestamigos.usuario.infraestructure.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteMapper clienteMapper;
    private final ClienteDataJpaRepository repository;

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        ClienteData data = clienteMapper.toData(cliente);
        ClienteData guardado = repository.save(data);
        return clienteMapper.toCliente(guardado);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return repository.findAll().stream()
                .map(clienteMapper::toCliente)
                .collect(Collectors.toList());
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .map(clienteMapper::toCliente)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cliente no encontrado con ID: " + id)
                );
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(clienteMapper::toCliente)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cliente no encontrado con correo: " + email)
                );
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        if (cliente.getId() == null)
            throw new IllegalArgumentException("El ID del cliente es obligatorio para actualizar.");

        if (!repository.existsById(cliente.getId()))
            throw new IllegalArgumentException("No existe un cliente con ID: " + cliente.getId());

        ClienteData data = clienteMapper.toData(cliente);
        ClienteData actualizado = repository.save(data);
        return clienteMapper.toCliente(actualizado);
    }

    @Override
    public void eliminarCliente(Long id) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("No existe un cliente con ID: " + id);
        repository.deleteById(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public Cliente buscarPorToken(String token) {
        ClienteData data = repository.findByTokenConfirmacion(token)
                .orElseThrow(() -> new RuntimeException("Cliente con token " + token + " no encontrado"));
        return clienteMapper.toCliente(data);
    }
}