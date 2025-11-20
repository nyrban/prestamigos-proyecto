package com.prestamigos.prestamo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {

    private Long id;

    private String tipo;
    private String descripcion;

    private Long vendedorId;
    private Long clienteId;
    private Long administradorId; // admin que aprueba o rechaza

    private Double monto = 0.0;
    private String metodo;

    private String estado;// aprobado, rechazado

    private LocalDate fechaSolicitud; // fecha en que el cliente crea la solicitud
    private LocalDate fechaResolucion; // fecha en que el admin aprueba/rechaza
}