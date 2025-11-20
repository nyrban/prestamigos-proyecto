package com.prestamigos.usuario.domain.usecase;

import com.prestamigos.usuario.domain.model.Administrador;
import com.prestamigos.usuario.domain.model.gateway.AdministradorGateway;
import com.prestamigos.usuario.domain.model.gateway.EncrypterGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdministradorUseCase {

    private final AdministradorGateway administradorGateway;
    private final EncrypterGateway encrypterGateway;
    private final CorreoService correoService;//confirma token

    public Administrador guardarAdministrador(Administrador administrador) {

        if (administrador == null) {
            throw new IllegalArgumentException("Los datos del administrador no pueden estar vacíos.");
        }
        if (administrador.getNombre() == null || administrador.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (administrador.getEmail() == null || administrador.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (!administrador.getEmail().contains("@") || !administrador.getEmail().endsWith(".com")) {
            throw new IllegalArgumentException("El correo ingresado no tiene un formato válido, debe contener '@' y terminar en '.com'.");
        }
        if (administrador.getPassword() == null || administrador.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (administrador.getPassword().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }
        if (administrador.getTelefono() == null || administrador.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono es obligatorio.");
        }
        if (administrador.getTelefono().length() < 10) {
            throw new IllegalArgumentException("El telefono debe tener 10 caracteres.");
        }
        if (administrador.getCiudad() == null || administrador.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso de la ciudad es obligatorio.");
        }
        if (administrador.getDepartamento() == null || administrador.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso del departamento es obligatorio.");
        }
        if (administrador.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        LocalDate hoy = LocalDate.now();
        if (administrador.getFechaNacimiento().plusYears(18).isAfter(hoy)) {
            throw new IllegalArgumentException("El administrador debe ser mayor de 18 años.");
        }
        if (administradorGateway.existePorEmail(administrador.getEmail())) {
            throw new IllegalArgumentException("Ya existe un administrador con el correo: " + administrador.getEmail());
        }
        if (!administrador.getPassword().startsWith("$2a$")) {
            administrador.setPassword(encrypterGateway.encrypt(administrador.getPassword()));
        }

        administrador.setActivo(false);
        administrador.setTokenConfirmacion(UUID.randomUUID().toString());

        Administrador guardado = administradorGateway.guardarAdministrador(administrador);
        correoService.enviarCorreoConfirmacion(guardado);

        return guardado;
    }

    public List<Administrador> buscarTodos() {
        return administradorGateway.buscarTodos();
    }

    public String eliminarAdministradorPorId(Long id) {
        try {
            administradorGateway.eliminarAdministrador(id);
            return "Administrador eliminado correctamente con ID: " + id;
        } catch (Exception error) {
            throw new IllegalArgumentException("Error al eliminar administrador: " + error.getMessage());
        }
    }

    public Administrador buscarPorId(Long id) {
        Administrador administrador = administradorGateway.buscarPorId(id);
        if (administrador == null) {
            throw new IllegalArgumentException("Administrador no encontrado con ID: " + id);
        }
        return administrador;
    }

    public Administrador buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar un correo válido para la búsqueda.");
        }

        Administrador administrador = administradorGateway.buscarPorEmail(email);
        if (administrador == null) {
            throw new IllegalArgumentException("No existe un administrador con el correo: " + email);
        }
        return administrador;
    }

    public Administrador buscarPorToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token inválido.");
        }
        Administrador administrador = administradorGateway.buscarPorToken(token);
        if (administrador == null) {
            throw new IllegalArgumentException("Token inválido o administrador no encontrado.");
        }
        return administrador;
    }


    public Administrador actualizarAdministrador(Administrador administrador) {

        if (administrador == null) {
            throw new IllegalArgumentException("Los datos del administrador no pueden estar vacíos.");
        }
        if (administrador.getId() == null) {
            throw new IllegalArgumentException("El ID es obligatorio para actualizar el administrador.");
        }
        if (administrador.getNombre() == null || administrador.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (administrador.getEmail() == null || administrador.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (!administrador.getEmail().contains("@") || !administrador.getEmail().endsWith(".com")) {
            throw new IllegalArgumentException("El correo ingresado no tiene un formato válido, debe contener '@' y terminar en '.com'.");
        }
        if (administrador.getPassword() == null || administrador.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (administrador.getPassword().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }
        if (administrador.getTelefono() == null || administrador.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono es obligatorio.");
        }
        if (administrador.getTelefono().length() < 10) {
            throw new IllegalArgumentException("El telefono debe tener 10 caracteres.");
        }
        if (administrador.getCiudad() == null || administrador.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso de la ciudad es obligatorio.");
        }
        if (administrador.getDepartamento() == null || administrador.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso del departamento es obligatorio.");
        }
        if (administrador.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        LocalDate hoy = LocalDate.now();
        if (administrador.getFechaNacimiento().plusYears(18).isAfter(hoy)) {
            throw new IllegalArgumentException("El administrador debe ser mayor de 18 años.");
        }

        Administrador existente = administradorGateway.buscarPorId(administrador.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No existe un administrador con el ID: " + administrador.getId());
        }

        if (!administrador.getEmail().equals(existente.getEmail())) {
            if (administradorGateway.existePorEmail(administrador.getEmail())) {
                throw new IllegalArgumentException("Ya existe un administrador con el correo: " + administrador.getEmail());
            }
        }

        if (administrador.getPassword() != null && !administrador.getPassword().isBlank()) {
            if (!administrador.getPassword().startsWith("$2a$")) {
                administrador.setPassword(encrypterGateway.encrypt(administrador.getPassword()));
            }
        } else {
            administrador.setPassword(existente.getPassword());
        }

        if (administrador.getActivo() == null) {
            administrador.setActivo(existente.getActivo());
        }

        return administradorGateway.actualizarAdministrador(administrador);
    }

    public Administrador buscarPorEmail(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo y la contraseña son obligatorios para iniciar sesión.");
        }

        Administrador administrador = administradorGateway.buscarPorEmail(email);
        if (administrador == null) {
            throw new IllegalArgumentException("Administrador no encontrado con el correo: " + email);
        }

        if (!administrador.getActivo()) {
            throw new IllegalStateException("Debes confirmar tu correo antes de iniciar sesión.");
        }

        if (!encrypterGateway.checkPass(password, administrador.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        return administrador;
    }

}