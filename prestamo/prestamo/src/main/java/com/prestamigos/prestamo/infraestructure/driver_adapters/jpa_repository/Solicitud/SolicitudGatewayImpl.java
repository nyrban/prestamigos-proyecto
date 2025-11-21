package com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Solicitud;

import com.prestamigos.prestamo.domain.model.Solicitud;
import com.prestamigos.prestamo.domain.model.gateway.SolicitudGateway;
import com.prestamigos.prestamo.infraestructure.mapper.SolicitudMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SolicitudGatewayImpl implements SolicitudGateway {

    private final SolicitudMapper solicitudMapper;
    private final SolicitudDataJpaRepository repository;
    private final RestTemplate restTemplate;

    @Override
    public Solicitud guardarSolicitud(Solicitud solicitud) {

        if (solicitud.getClienteId() != null) {

            String url = "http://localhost:8083/api/prestaamigos/cliente/" + solicitud.getClienteId();


            try {
                // Consumir cliente del microservicio clientes
                Map<String, Object> clienteResponse =
                        restTemplate.getForObject(url, Map.class);

                if (clienteResponse == null) {
                    throw new RuntimeException("Respuesta vacía del microservicio clientes.");
                }

                // Extraer vendedorId del cliente
                Object vendId = clienteResponse.get("vendedorId");

                if (vendId == null) {
                    throw new IllegalArgumentException(
                            "El cliente con ID " + solicitud.getClienteId() + " no tiene vendedor asignado."
                    );
                }

                Long vendedorId = Long.valueOf(vendId.toString());

                // Asignar el vendedorId a la solicitud
                solicitud.setVendedorId(vendedorId);

            } catch (HttpClientErrorException.NotFound e) {
                throw new IllegalArgumentException(
                        "El cliente con ID " + solicitud.getClienteId() + " no existe."
                );
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error conectando con microservicio clientes: " + e.getMessage()
                );
            }
        }

        SolicitudData solicitudData = solicitudMapper.toData(solicitud);
        SolicitudData guardado = repository.save(solicitudData);

        return solicitudMapper.toSolicitud(guardado);
    }

    @Override
    public Optional<Solicitud> buscarPorId(Long id) {
        return repository.findById(id)
                .map(solicitudMapper::toSolicitud);
    }

    @Override
    public List<Solicitud> buscarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(solicitudMapper::toSolicitud)
                .toList();
    }

    @Override
    public List<Solicitud> buscarTodasPendientes() {
        return repository.findByEstado("PENDIENTE")
                .stream()
                .map(solicitudMapper::toSolicitud)
                .toList();
    }

    @Override
    public Solicitud actualizarEstado(Long id, String nuevoEstado, Long administradorId) {

        SolicitudData data = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Solicitud no encontrada con ID: " + id)
                );

        data.setEstado(nuevoEstado);
        data.setAdministradorId(administradorId);
        data.setFechaResolucion(java.time.LocalDate.now());

        SolicitudData actualizado = repository.save(data);
        return solicitudMapper.toSolicitud(actualizado);
    }

    @Override
    public List<Solicitud> buscarTodas() {
        return repository.findAll()
                .stream()
                .map(solicitudMapper::toSolicitud)
                .toList();
    }

    @Override
    public void eliminarSolicitud(Long id) {
        if (!repository.existsById(id))
            throw new IllegalArgumentException("No existe una solicitud con el ID: " + id);
        repository.deleteById(id);
    }
}
