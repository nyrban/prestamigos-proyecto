package com.prestamigos.pago.infraestructure.driver_adapters.jpa_repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prestamo_id", nullable = false)
    private Long prestamoId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private String metodo;  // efectivo, nequi, tarjeta, pse

    @Column(name = "tipo_pago", nullable = false)
    private String tipoPago; // cuota, abono capital, pago total

    @Column(nullable = false)
    private Double monto; // valor pagado

    @Column(nullable = false)
    private LocalDate fecha;
}