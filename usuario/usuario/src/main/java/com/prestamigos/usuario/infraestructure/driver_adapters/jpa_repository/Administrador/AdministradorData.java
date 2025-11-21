package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Administrador;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "administradores")
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    private LocalDate fechaNacimiento;
    private String direccion;
    private String ciudad;
    private String departamento;
    private String telefono;

    private boolean activo;
    private String tokenConfirmacion;
}
