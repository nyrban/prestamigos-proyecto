package com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Prestamo;

import com.prestamigos.prestamo.domain.model.Prestamo;
import com.prestamigos.prestamo.domain.model.gateway.PrestamoGateway;
import com.prestamigos.prestamo.infraestructure.mapper.PrestamoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PrestamoGatewayImpl implements PrestamoGateway {

    private final PrestamoMapper prestamoMapper;
    private final PrestamoDataJpaRepository repository;
    private final RestTemplate restTemplate;

    @Override
    public Prestamo guardarPrestamo(Prestamo prestamo) {

        // Validar cliente y asignar vendedorId automáticamente
        if (prestamo.getClienteId() != null) {

            String url = "http://localhost:8083/api/prestaamigos/cliente/" + prestamo.getClienteId();

            try {
                // Consumir microservicio cliente
                Map<String, Object> clienteResponse =
                        restTemplate.getForObject(url, Map.class);

                if (clienteResponse == null) {
                    throw new RuntimeException("Respuesta vacía del microservicio clientes.");
                }

                // Extraer vendedorId del cliente
                Object vendId = clienteResponse.get("vendedorId");

                if (vendId == null) {
                    throw new IllegalArgumentException(
                            "El cliente con ID " + prestamo.getClienteId() + " no tiene vendedor asignado."
                    );
                }

                Long vendedorId = Long.valueOf(vendId.toString());

                // Asignar vendedorId
                prestamo.setVendedorId(vendedorId);

            } catch (HttpClientErrorException.NotFound e) {
                throw new IllegalArgumentException(
                        "El cliente con ID " + prestamo.getClienteId() + " no existe."
                );
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error conectando con microservicio clientes: " + e.getMessage()
                );
            }
        }

        // Guardar el préstamo
        PrestamoData data = prestamoMapper.toData(prestamo);
        PrestamoData guardado = repository.save(data);

        return prestamoMapper.toPrestamo(guardado);
    }

    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return repository.findById(id)
                .map(prestamoMapper::toPrestamo);
    }

    @Override
    public List<Prestamo> listarTodos() {
        return repository.findAll()
                .stream()
                .map(prestamoMapper::toPrestamo)
                .toList();
    }

    @Override
    public List<Prestamo> listarPorVendedorId(Long vendedorId) {
        return repository.findByVendedorId(vendedorId)
                .stream()
                .map(prestamoMapper::toPrestamo)
                .toList();
    }

    @Override
    public Prestamo actualizarPrestamo(Prestamo prestamo) {

        if (!repository.existsById(prestamo.getId())) {
            throw new IllegalArgumentException("No existe un préstamo con ID: " + prestamo.getId());
        }

        PrestamoData data = prestamoMapper.toData(prestamo);
        PrestamoData actualizado = repository.save(data);

        return prestamoMapper.toPrestamo(actualizado);
    }

    @Override
    public void eliminarPrestamo(Long id) {

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No existe un préstamo con ID: " + id);
        }

        repository.deleteById(id);
    }
}
