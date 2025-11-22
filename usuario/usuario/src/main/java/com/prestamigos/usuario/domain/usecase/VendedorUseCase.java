package com.prestamigos.usuario.domain.usecase;

import com.prestamigos.usuario.domain.model.Vendedor;
import com.prestamigos.usuario.domain.model.gateway.EncrypterGateway;
import com.prestamigos.usuario.domain.model.gateway.VendedorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendedorUseCase {

    private final VendedorGateway vendedorGateway;
    private final EncrypterGateway encrypterGateway;
    private final CorreoService correoService;

    public Vendedor guardarVendedor(Vendedor vendedor) {

        if (vendedor == null) {
            throw new IllegalArgumentException("Los datos del vendedor no pueden estar vacíos.");
        }
        if (vendedor.getNombre() == null || vendedor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (vendedor.getEmail() == null || vendedor.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (!vendedor.getEmail().contains("@") || !vendedor.getEmail().endsWith(".com")) {
            throw new IllegalArgumentException("El correo ingresado no tiene un formato válido. Debe contener '@' y terminar en '.com'.");
        }
        if (vendedor.getPassword() == null || vendedor.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (vendedor.getPassword().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }
        if (vendedor.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }

        LocalDate hoy = LocalDate.now();
        if (vendedor.getFechaNacimiento().plusYears(18).isAfter(hoy)) {
            throw new IllegalArgumentException("El vendedor debe ser mayor de 18 años.");
        }
        if (vendedor.getCiudad() == null || vendedor.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso de la ciudad es obligatorio.");
        }
        if (vendedor.getDepartamento() == null || vendedor.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El ingreso del departamento es obligatorio.");
        }
        if (vendedor.getTelefono().length() < 10) {
            throw new IllegalArgumentException("El telefono debe tener 10 caracteres.");
        }
        if (vendedorGateway.existePorEmail(vendedor.getEmail())) {
            throw new IllegalArgumentException("Ya existe un vendedor con el correo: " + vendedor.getEmail());
        }

        if (vendedor.getCupoTotal() == null || vendedor.getCupoTotal() <= 0) {
            throw new IllegalArgumentException("El cupoTotal debe ser mayor que 0.");
        }
        if (vendedor.getCupoTotal() > 5_000_000) {
            throw new IllegalArgumentException("El cupoTotal no puede exceder los 5 millones.");
        }
        if (vendedor.getCupoDisponible() == null || vendedor.getCupoDisponible() <= 0) {
            throw new IllegalArgumentException("El cupoDisponible debe ser mayor que 0.");
        }
        if (vendedor.getCupoDisponible() > vendedor.getCupoTotal()) {
            throw new IllegalArgumentException("El cupoDisponible no puede ser mayor que el cupoTotal.");
        }
        if (vendedor.getGananciasAcumuladas() == null || vendedor.getGananciasAcumuladas() < 0) {
            throw new IllegalArgumentException("Las ganancias acumuladas no pueden ser negativas.");
        }


        if (!vendedor.getPassword().startsWith("$2a$")) {
            vendedor.setPassword(encrypterGateway.encrypt(vendedor.getPassword()));
        }

        vendedor.setActivo(false);
        vendedor.setTokenConfirmacion(UUID.randomUUID().toString());

        Vendedor guardado = vendedorGateway.guardarVendedor(vendedor);
        correoService.enviarCorreoConfirmacion(guardado);

        return guardado;
    }


    public List<Vendedor> buscarTodos() {
        return vendedorGateway.buscarTodos();
    }

    public String eliminarVendedorPorId(Long id) {
        try {
            vendedorGateway.eliminarVendedor(id);
            return "Vendedor eliminado correctamente con ID: " + id;
        } catch (Exception error) {
            throw new IllegalArgumentException("Error al eliminar vendedor: " + error.getMessage());
        }
    }

    public Vendedor buscarPorId(Long id) {
        Vendedor vendedor = vendedorGateway.buscarPorId(id);
        if (vendedor == null) {
            throw new IllegalArgumentException("Vendedor no encontrado con ID: " + id);
        }
        return vendedor;
    }

    public Vendedor buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar un correo válido para la búsqueda.");
        }

        Vendedor vendedor = vendedorGateway.buscarPorEmail(email);
        if (vendedor == null) {
            throw new IllegalArgumentException("No existe un vendedor con el correo: " + email);
        }
        return vendedor;
    }

    public Vendedor buscarPorToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token inválido.");
        }
        Vendedor vendedor = vendedorGateway.buscarPorToken(token);
        if (vendedor == null) {
            throw new IllegalArgumentException("Token inválido o vendedor no encontrado.");
        }
        return vendedor;
    }


    public Vendedor actualizarVendedor(Vendedor vendedor) {

        if (vendedor == null) {
            throw new IllegalArgumentException("Los datos del vendedor no pueden estar vacíos.");
        }
        if (vendedor.getId() == null) {
            throw new IllegalArgumentException("El ID del vendedor es obligatorio para actualizar.");
        }
        Vendedor existente = vendedorGateway.buscarPorId(vendedor.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No existe un vendedor con el ID: " + vendedor.getId());
        }
        if (vendedor.getNombre() != null && vendedor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (vendedor.getEmail() != null) {
            if (vendedor.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("El correo no puede estar vacío.");
            }
            if (!vendedor.getEmail().contains("@") || !vendedor.getEmail().endsWith(".com")) {
                throw new IllegalArgumentException("El correo ingresado no tiene un formato válido.");
            }

            if (!vendedor.getEmail().equals(existente.getEmail())
                    && vendedorGateway.existePorEmail(vendedor.getEmail())) {
                throw new IllegalArgumentException("Ya existe un vendedor con el correo: " + vendedor.getEmail());
            }
        } else {
            vendedor.setEmail(existente.getEmail());
        }

        if (vendedor.getFechaNacimiento() != null) {
            LocalDate hoy = LocalDate.now();
            if (vendedor.getFechaNacimiento().plusYears(18).isAfter(hoy)) {
                throw new IllegalArgumentException("El vendedor debe ser mayor de 18 años.");
            }
        } else {
            vendedor.setFechaNacimiento(existente.getFechaNacimiento());
        }

        if (vendedor.getCiudad() != null && vendedor.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía.");
        } else if (vendedor.getCiudad() == null) {
            vendedor.setCiudad(existente.getCiudad());
        }

        if (vendedor.getDepartamento() != null && vendedor.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El departamento no puede estar vacío.");
        } else if (vendedor.getDepartamento() == null) {
            vendedor.setDepartamento(existente.getDepartamento());
        }

        if (vendedor.getTelefono() != null) {
            if (vendedor.getTelefono().length() < 10) {
                throw new IllegalArgumentException("El teléfono debe tener al menos 10 dígitos.");
            }
        } else {
            vendedor.setTelefono(existente.getTelefono());
        }


        if (vendedor.getPassword() != null && !vendedor.getPassword().isBlank()) {
            if (!vendedor.getPassword().startsWith("$2a$")) {
                vendedor.setPassword(encrypterGateway.encrypt(vendedor.getPassword()));
            }
        } else {
            vendedor.setPassword(existente.getPassword());
        }

        if (vendedor.getActivo() == null) {
            vendedor.setActivo(existente.getActivo());
        }

        return vendedorGateway.actualizarVendedor(vendedor);
    }


    public Vendedor buscarPorEmail(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo y la contraseña son obligatorios para iniciar sesión.");
        }

        Vendedor vendedor = vendedorGateway.buscarPorEmail(email);
        if (vendedor == null) {
            throw new IllegalArgumentException("Vendedor no encontrado con el correo: " + email);
        }

        if (!vendedor.getActivo()) {
            throw new IllegalStateException("Debes confirmar tu correo antes de iniciar sesión.");
        }

        if (!encrypterGateway.checkPass(password, vendedor.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        return vendedor;
    }

}

