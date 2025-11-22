package com.prestamigos.pago.infraestructure.driver_adapters.jpa_repository;

import com.prestamigos.pago.domain.model.Pago;
import com.prestamigos.pago.domain.model.gateway.PagoGateway;
import com.prestamigos.pago.infraestructure.mapper.PagoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PagoGatewayImpl implements PagoGateway {

    private final PagoMapper pagoMapper;
    private final PagoDataJpaRepository repository;
    private final RestTemplate restTemplate;

    // ============================================================
    // GUARDAR PAGO
    // ============================================================
    @Override
    public Pago guardarPago(Pago pago) {

        String urlPrestamo = "http://localhost:7001/api/prestamigos/prestamos/" + pago.getPrestamoId();

        try {
            Map<String, Object> prestamoResponse =
                    restTemplate.getForObject(urlPrestamo, Map.class);

            // 🔥 DEBUG para evitar 500 y ver qué devuelve realmente
            System.out.println("DEBUG PRESTAMO RESPONSE: " + prestamoResponse);

            if (prestamoResponse == null) {
                throw new RuntimeException("Respuesta vacía del microservicio préstamos.");
            }

            // INTENTAR LEER clienteId DIRECTO
            Object clienteIdPrestamo = prestamoResponse.get("clienteId");

            // SI clienteId viene nulo, INTENTAR otras claves conocidas
            if (clienteIdPrestamo == null) {
                clienteIdPrestamo = prestamoResponse.get("cliente_id"); // por si viene en snake_case
            }
            if (clienteIdPrestamo == null) {
                clienteIdPrestamo = prestamoResponse.get("cliente"); // por si viene como objeto o id
            }
            if (clienteIdPrestamo == null) {
                throw new IllegalArgumentException(
                        "El préstamo con ID " + pago.getPrestamoId() + " no tiene cliente asociado."
                );
            }

            Long clienteIdReal = Long.valueOf(clienteIdPrestamo.toString());

            // Asignar clienteId REAL AL PAGO (por si el body trae algo diferente)
            pago.setClienteId(clienteIdReal);

        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException(
                    "El préstamo con ID " + pago.getPrestamoId() + " no existe."
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error conectando con microservicio préstamos: " + e.getMessage()
            );
        }

        // Guardar pago en la base de datos local
        PagoData data = pagoMapper.toData(pago);
        PagoData guardado = repository.save(data);

        return pagoMapper.toPago(guardado);
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    @Override
    public Optional<Pago> buscarPorId(Long id) {
        return repository.findById(id)
                .map(pagoMapper::toPago);
    }

    // ============================================================
    // LISTAR TODOS
    // ============================================================
    @Override
    public List<Pago> listarTodos() {
        return repository.findAll()
                .stream()
                .map(pagoMapper::toPago)
                .toList();
    }

    // ============================================================
    // LISTAR POR CLIENTE ID
    // ============================================================
    @Override
    public List<Pago> listarPorClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId)
                .stream()
                .map(pagoMapper::toPago)
                .toList();
    }

    // ============================================================
    // LISTAR POR PRESTAMO ID
    // ============================================================
    @Override
    public List<Pago> listarPorPrestamoId(Long prestamoId) {
        return repository.findByPrestamoId(prestamoId)
                .stream()
                .map(pagoMapper::toPago)
                .toList();
    }

    // ============================================================
    // LISTAR POR FECHA
    // ============================================================
    @Override
    public List<Pago> listarPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha)
                .stream()
                .map(pagoMapper::toPago)
                .toList();
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================
    @Override
    public Pago actualizarPago(Pago pago) {

        if (!repository.existsById(pago.getId())) {
            throw new IllegalArgumentException("No existe un pago con ID: " + pago.getId());
        }

        PagoData data = pagoMapper.toData(pago);
        PagoData actualizado = repository.save(data);

        return pagoMapper.toPago(actualizado);
    }

    // ============================================================
    // ELIMINAR
    // ============================================================
    @Override
    public void eliminarPago(Long id) {

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No existe un pago con ID: " + id);
        }

        repository.deleteById(id);
    }
}
