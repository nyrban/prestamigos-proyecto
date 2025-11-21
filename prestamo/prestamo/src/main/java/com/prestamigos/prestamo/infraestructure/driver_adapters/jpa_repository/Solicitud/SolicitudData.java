package com.prestamigos.prestamo.infraestructure.driver_adapters.jpa_repository.Solicitud;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "solicitudes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SolicitudData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String descripcion;

    @Column(name = "vendedor_id")
    private Long vendedorId;

    @Column(name = "usuario_id")
    private Long clienteId;

    @Column(name = "administrador_id")
    private Long administradorId;

    private Double monto;

    private String metodo;

    private String estado;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private LocalDate fechaResolucion;
}
