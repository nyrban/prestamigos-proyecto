package com.prestamigos.prestamo.infraestructure.entry_points;

import com.prestamigos.prestamo.domain.model.Prestamo;
import com.prestamigos.prestamo.domain.usecase.PrestamoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamigos/prestamos")
@RequiredArgsConstructor

public class PrestamoController {

    private final PrestamoUseCase prestamoUseCase;

    @PostMapping
    public ResponseEntity<?> guardarPrestamo(@RequestBody Prestamo prestamo) {
        try {
            Prestamo creado = prestamoUseCase.guardarPrestamo(prestamo);
            return ResponseEntity.ok("Préstamo creado correctamente con ID: " + creado.getId());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prestamoUseCase.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(prestamoUseCase.listarTodos());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<?> listarPorVendedorId(@PathVariable Long vendedorId) {
        try {
            return ResponseEntity.ok(prestamoUseCase.listarPorVendedorId(vendedorId));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizarPrestamo(@RequestBody Prestamo prestamo) {
        try {
            Prestamo actualizado = prestamoUseCase.actualizarPrestamo(prestamo);
            return ResponseEntity.ok("Préstamo actualizado correctamente con ID: " + actualizado.getId());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrestamo(@PathVariable Long id) {
        try {
            prestamoUseCase.eliminarPrestamo(id);
            return ResponseEntity.ok("Préstamo eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
