package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Vendedor;

import com.prestamigos.usuario.domain.model.Vendedor;
import com.prestamigos.usuario.domain.model.gateway.VendedorGateway;
import com.prestamigos.usuario.infraestructure.mapper.VendedorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class VendedorGatewayImpl implements VendedorGateway {

    private final VendedorMapper vendedorMapper;
    private final VendedorDataJpaRepository repository;

    @Override
    public Vendedor guardarVendedor(Vendedor vendedor) {
        VendedorData data = vendedorMapper.toData(vendedor);
        VendedorData guardado = repository.save(data);
        return vendedorMapper.toVendedor(guardado);
    }

    @Override
    public List<Vendedor> buscarTodos() {
        return repository.findAll().stream()
                .map(vendedorMapper::toVendedor)
                .collect(Collectors.toList());
    }

    @Override
    public Vendedor buscarPorId(Long id) {
        return repository.findById(id)
                .map(vendedorMapper::toVendedor)
                .orElseThrow(() ->
                        new IllegalArgumentException("Vendedor no encontrado con ID: " + id)
                );
    }

    @Override
    public Vendedor buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(vendedorMapper::toVendedor)
                .orElseThrow(() ->
                        new IllegalArgumentException("Vendedor no encontrado con correo: " + email)
                );
    }

    @Override
    public Vendedor actualizarVendedor(Vendedor vendedor) {
        if (vendedor.getId() == null)
            throw new IllegalArgumentException("El ID del vendedor es obligatorio para actualizar.");

        if (!repository.existsById(vendedor.getId()))
            throw new IllegalArgumentException("No existe un vendedor con ID: " + vendedor.getId());

        VendedorData data = vendedorMapper.toData(vendedor);
        VendedorData actualizado = repository.save(data);
        return vendedorMapper.toVendedor(actualizado);
    }

    @Override
    public void eliminarVendedor(Long id) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("No existe un vendedor con ID: " + id);
        repository.deleteById(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public Vendedor buscarPorToken(String token) {
        VendedorData data = repository.findByTokenConfirmacion(token)
                .orElseThrow(() -> new RuntimeException("Vendedor con token " + token + " no encontrado"));
        return vendedorMapper.toVendedor(data);
    }

    @Override
    public boolean existeVendedor(Long vendedorId) {
        return repository.existsById(vendedorId);
    }
}
