package com.prestamigos.usuario.infraestructure.entry_points;

import com.prestamigos.usuario.domain.model.Administrador;
import com.prestamigos.usuario.domain.model.Cliente;
import com.prestamigos.usuario.domain.usecase.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestaamigos/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @PostMapping("/save")
    public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
        try {
            clienteUseCase.guardarCliente(cliente);
            return ResponseEntity.ok("Cliente registrado correctamente. Revisa tu correo para confirmar la cuenta.");
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> listarTodosLosClientes() {
        List<Cliente> clientes = clienteUseCase.buscarTodos();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClienteById(@PathVariable Long id) {
        try {
            clienteUseCase.eliminarClientePorId(id);
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdCliente(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteUseCase.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(clienteUseCase.buscarPorEmail(email));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> actualizarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteUseCase.actualizarCliente(cliente);
            return ResponseEntity.ok("cliente actualizado correctamente con ID: " + clienteActualizado.getId());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCliente(@RequestParam String email, @RequestParam String password) {
        try {
            Cliente cliente = clienteUseCase.buscarPorEmail(email, password);
            return ResponseEntity.ok("Login exitoso. Bienvenido, " + cliente.getNombre() + ".");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmarCliente(@RequestParam String token) {
        try {
            Cliente cliente = clienteUseCase.buscarPorToken(token);
            cliente.setActivo(true);
            cliente.setTokenConfirmacion(null);
            clienteUseCase.actualizarCliente(cliente);
            return ResponseEntity.ok(" Cliente confirmado correctamente. Ahora puedes iniciar sesión.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
