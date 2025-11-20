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
public class Prestamo {

    private Long id;

    private Long clienteId; // Cliente
    private Long vendedorId;

    private String productoNombre;
    private BigDecimal montoProducto;
    private BigDecimal montoPrestamo;

    private Integer numCuotas; // número de cuotas
    private String periodoTipo; // semanal, quincenal, mensual
    private BigDecimal cuotaValor;

    private String estado;// activo, pagado, cancelado

    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
}