package com.prestamigos.prestamo.infraestructure.entry_points;

import com.prestamigos.prestamo.domain.model.Solicitud;
import com.prestamigos.prestamo.domain.usecase.SolicitudUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/prestamigos/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudUseCase solicitudUseCase;


    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody Solicitud solicitud) {
        try {
            Solicitud creada = solicitudUseCase.crearSolicitud(solicitud);
            return ResponseEntity.ok("Solicitud creada correctamente con ID: " + creada.getId());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(solicitudUseCase.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> buscarPorCliente(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(solicitudUseCase.buscarPorCliente(clienteId));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<?> buscarPendientes() {
        try {
            return ResponseEntity.ok(solicitudUseCase.buscarTodasPendientes());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/estado")
    public ResponseEntity<?> cambiarEstado(
            @RequestParam Long id,
            @RequestParam String nuevoEstado,
            @RequestParam Long administradorId
    ) {
        try {
            Solicitud actualizada = solicitudUseCase.cambiarEstado(id, nuevoEstado, administradorId);
            return ResponseEntity.ok("Estado actualizado correctamente para la solicitud: " + actualizada.getId());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSolicitudById(@PathVariable Long id) {
        try {
            solicitudUseCase.eliminarSolicitudPorId(id);
            return ResponseEntity.ok("Solicitud eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}

