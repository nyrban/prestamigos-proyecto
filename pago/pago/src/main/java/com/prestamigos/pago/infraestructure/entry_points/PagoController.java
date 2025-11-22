package com.prestamigos.pago.infraestructure.entry_points;

import com.prestamigos.pago.domain.model.Pago;
import com.prestamigos.pago.domain.usecase.PagoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prestamigos/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoUseCase pagoUseCase;

    // -----------------------------------------------------------
    // GUARDAR
    // -----------------------------------------------------------
    @PostMapping("/save")
    public ResponseEntity<?> guardar(@RequestBody Pago pago) {
        Object respuesta = pagoUseCase.guardarPago(pago);
        return ResponseEntity.ok(respuesta);
    }

    // -----------------------------------------------------------
    // BUSCAR POR ID
    // -----------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoUseCase.buscarPorId(id));
    }

    // -----------------------------------------------------------
    // LISTAR TODOS
    // -----------------------------------------------------------
    @GetMapping("/all")
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(pagoUseCase.listarTodos());
    }

    // -----------------------------------------------------------
    // LISTAR POR CLIENTE
    // -----------------------------------------------------------
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pagoUseCase.listarPorClienteId(clienteId));
    }

    // -----------------------------------------------------------
    // LISTAR POR PRESTAMO
    // -----------------------------------------------------------
    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<?> listarPorPrestamo(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(pagoUseCase.listarPorPrestamoId(prestamoId));
    }

    // -----------------------------------------------------------
    // LISTAR POR FECHA
    // -----------------------------------------------------------
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> listarPorFecha(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(pagoUseCase.listarPorFecha(fecha));
    }

    // -----------------------------------------------------------
    // ACTUALIZAR
    // -----------------------------------------------------------
    @PutMapping("/update")
    public ResponseEntity<?> actualizar(@RequestBody Pago pago) {
        return ResponseEntity.ok(pagoUseCase.actualizarPago(pago));
    }

    // -----------------------------------------------------------
    // ELIMINAR
    // -----------------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(pagoUseCase.eliminarPago(id));
    }
}

