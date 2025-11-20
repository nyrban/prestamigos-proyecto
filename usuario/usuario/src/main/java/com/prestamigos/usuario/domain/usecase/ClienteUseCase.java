package com.prestamigos.usuario.domain.usecase;


import com.prestamigos.usuario.domain.model.Cliente;
import com.prestamigos.usuario.domain.model.gateway.ClienteGateway;
import com.prestamigos.usuario.domain.model.gateway.EncrypterGateway;
import com.prestamigos.usuario.domain.model.gateway.VendedorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final EncrypterGateway encrypterGateway;
    private final CorreoService correoService;
    private final VendedorGateway vendedorGateway;

    public Cliente guardarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("Los datos del cliente no pueden estar vacíos.");
        }
        if (cliente.getVendedorId() == null) {
            throw new IllegalArgumentException("El ID del vendedor es obligatorio para registrar un cliente.");
        }
        if (!vendedorGateway.existeVendedor(cliente.getVendedorId())) {
            throw new IllegalArgumentException("El vendedor con ID " + cliente.getVendedorId() + " no existe.");
        }
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (!cliente.getEmail().contains("@") || !cliente.getEmail().endsWith(".com")) {
            throw new IllegalArgumentException("El correo ingresado no tiene un formato válido. Debe contener '@' y terminar en '.com'.");
        }
        if (cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (cliente.getPassword().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }
        if (cliente.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        LocalDate hoy = LocalDate.now();
        if (cliente.getFechaNacimiento().plusYears(15).isAfter(hoy)) {
            throw new IllegalArgumentException("El cliente debe ser mayor de 15 años.");
        }
        if (clienteGateway.existePorEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con el correo: " + cliente.getEmail());
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono es obligatorio.");
        }
        if (cliente.getTelefono().length() < 10) {
            throw new IllegalArgumentException("El telefono debe tener 10 caracteres.");
        }
        if (cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso de la direccion es obligatorio.");
        }
        if (cliente.getCiudad() == null || cliente.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso de la ciudad es obligatorio.");
        }
        if (cliente.getDepartamento() == null || cliente.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso del departamento es obligatorio.");
        }

        if (!cliente.getPassword().startsWith("$2a$")) {
            cliente.setPassword(encrypterGateway.encrypt(cliente.getPassword()));
        }

        cliente.setActivo(false);
        cliente.setTokenConfirmacion(UUID.randomUUID().toString());

        Cliente guardado = clienteGateway.guardarCliente(cliente);
        correoService.enviarCorreoConfirmacion(guardado);

        return guardado;
    }

    public List<Cliente> buscarTodos() {
        return clienteGateway.buscarTodos();
    }

    public String eliminarClientePorId(Long id) {
        try {
            clienteGateway.eliminarCliente(id);
            return "Cliente eliminado correctamente con ID: " + id;
        } catch (Exception error) {
            throw new IllegalArgumentException("Error al eliminar cliente: " + error.getMessage());
        }
    }

    public Cliente buscarPorId(Long id) {
        Cliente cliente = clienteGateway.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + id);
        }
        return cliente;
    }

    public Cliente buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar un correo válido para la búsqueda.");
        }

        Cliente cliente = clienteGateway.buscarPorEmail(email);
        if (cliente == null) {
            throw new IllegalArgumentException("No existe un cliente con el correo: " + email);
        }
        return cliente;
    }


    public Cliente actualizarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("Los datos del cliente no pueden estar vacíos.");
        }
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("El ID es obligatorio para actualizar el cliente.");
        }
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (!cliente.getEmail().contains("@") || !cliente.getEmail().endsWith(".com")) {
            throw new IllegalArgumentException("El correo ingresado no tiene un formato válido, debe contener '@' y terminar en '.com'.");
        }
        if (cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (cliente.getPassword().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono es obligatorio.");
        }
        if (cliente.getTelefono().length() < 10) {
            throw new IllegalArgumentException("El telefono debe tener 10 caracteres.");
        }
        if (cliente.getCiudad() == null || cliente.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso de la ciudad es obligatorio.");
        }
        if (cliente.getDepartamento() == null || cliente.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso del departamento es obligatorio.");
        }
        if (cliente.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        LocalDate hoy = LocalDate.now();
        if (cliente.getFechaNacimiento().plusYears(18).isAfter(hoy)) {
            throw new IllegalArgumentException("El cliente debe ser mayor de 18 años.");
        }


        Cliente existente = clienteGateway.buscarPorId(cliente.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No existe un cliente con el ID: " + cliente.getId());
        }


        if (!cliente.getEmail().equals(existente.getEmail())) {
            if (clienteGateway.existePorEmail(cliente.getEmail())) {
                throw new IllegalArgumentException("Ya existe un administrador con el correo: " + cliente.getEmail());
            }
        }


        if (cliente.getPassword() != null && !cliente.getPassword().isBlank()) {
            if (!cliente.getPassword().startsWith("$2a$")) {
                cliente.setPassword(encrypterGateway.encrypt(cliente.getPassword()));
            }
        } else {
            cliente.setPassword(existente.getPassword());
        }


        if (cliente.getActivo() == null) {
            cliente.setActivo(existente.getActivo());
        }


        return clienteGateway.actualizarCliente(cliente);
    }

    public Cliente buscarPorEmail(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo y la contraseña son obligatorios para iniciar sesión.");
        }

        Cliente cliente = clienteGateway.buscarPorEmail(email);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado con el correo: " + email);
        }

        if (!cliente.getActivo()) {
            throw new IllegalStateException("Debes confirmar tu correo antes de iniciar sesión.");
        }

        if (!encrypterGateway.checkPass(password, cliente.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        return cliente;
    }

    public Cliente buscarPorToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token inválido.");
        }
        Cliente cliente = clienteGateway.buscarPorToken(token);
        if (cliente == null) {
            throw new IllegalArgumentException("Token inválido o cliente no encontrado.");
        }
        return cliente;
    }
}
