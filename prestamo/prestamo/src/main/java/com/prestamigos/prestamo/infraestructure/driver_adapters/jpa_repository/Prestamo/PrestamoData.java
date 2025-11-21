package com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Prestamo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrestamoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long clienteId;

    @Column(name = "vendedor_id")
    private Long vendedorId;

    @Column(name = "producto_nombre")
    private String productoNombre;

    @Column(name = "monto_producto")
    private BigDecimal montoProducto;

    @Column(name = "monto_prestamo")
    private BigDecimal montoPrestamo;

    @Column(name = "num_cuotas")
    private Integer numCuotas;

    @Column(name = "periodo_tipo")
    private String periodoTipo;

    @Column(name = "cuota_valor")
    private BigDecimal cuotaValor;

    private String estado;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_final")
    private LocalDate fechaFinal;
}
