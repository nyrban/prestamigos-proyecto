package com.prestamigos.usuario.infraestructure.entry_points;

import com.prestamigos.usuario.domain.model.Vendedor;
import com.prestamigos.usuario.domain.usecase.VendedorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestaamigos/vendedor")
@RequiredArgsConstructor
public class VendedorController {

    private final VendedorUseCase vendedorUseCase;

    @PostMapping("/save")
    public ResponseEntity<?> saveVendedor(@RequestBody Vendedor vendedor) {
        try {
            vendedorUseCase.guardarVendedor(vendedor);
            return ResponseEntity.ok("Vendedor registrado correctamente. Revisa tu correo para confirmar la cuenta.");
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> listarTodosLosVendedores() {
        List<Vendedor> vendedores = vendedorUseCase.buscarTodos();
        return ResponseEntity.ok(vendedores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendedorById(@PathVariable Long id) {
        try {
            vendedorUseCase.eliminarVendedorPorId(id);
            return ResponseEntity.ok("Vendedor eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdVendedor(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vendedorUseCase.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(vendedorUseCase.buscarPorEmail(email));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> actualizarVendedor(@RequestBody Vendedor vendedor) {
        try {
            Vendedor actualizado = vendedorUseCase.actualizarVendedor(vendedor);
            return ResponseEntity.ok("Vendedor actualizado correctamente con ID: " + actualizado.getId());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginVendedor(@RequestParam String email, @RequestParam String password) {
        try {
            Vendedor vendedor = vendedorUseCase.buscarPorEmail(email, password);
            return ResponseEntity.ok("Login exitoso. Bienvenido, " + vendedor.getNombre() + ".");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmarVendedor(@RequestParam String token) {
        try {
            Vendedor vendedor = vendedorUseCase.buscarPorToken(token);
            vendedor.setActivo(true);
            vendedor.setTokenConfirmacion(null);
            vendedorUseCase.actualizarVendedor(vendedor);
            return ResponseEntity.ok("Vendedor confirmado correctamente. Ahora puedes iniciar sesión.");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
