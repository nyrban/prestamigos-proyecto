package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class ClienteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    private LocalDate fechaNacimiento; // yyyy-MM-dd
    private String direccion;
    private String ciudad;
    private String departamento;
    private String telefono;

    private boolean activo;
    private String tokenConfirmacion;

    private Long vendedorId; // FK microservicio vendedor
}
