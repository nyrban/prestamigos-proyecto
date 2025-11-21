package com.prestamigos.usuario.infraestructure.entry_points;

import com.prestamigos.usuario.domain.model.Administrador;
import com.prestamigos.usuario.domain.usecase.AdministradorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestaamigos/administrador")
@RequiredArgsConstructor

public class AdministradorController {

    private final AdministradorUseCase administradorUseCase;

    @PostMapping("/save")
    public ResponseEntity<?> saveAdministrador(@RequestBody Administrador administrador) {
        try {
            administradorUseCase.guardarAdministrador(administrador);
            return ResponseEntity.ok("Administrador registrado correctamente. Revisa tu correo para confirmar la cuenta.");
        } catch (Exception ex) {

            return ResponseEntity.ok(ex.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> listarTodosLosAdministradores() {
        List<Administrador> administradores = administradorUseCase.buscarTodos();
        return ResponseEntity.ok(administradores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdministradorById(@PathVariable Long id) {
        try {
            administradorUseCase.eliminarAdministradorPorId(id);
            return ResponseEntity.ok("Administrador eliminado correctamente.");
        } catch (Exception e) {

            return ResponseEntity.ok(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdAdministrador(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(administradorUseCase.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(administradorUseCase.buscarPorEmail(email));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> actualizarAdministrador(@RequestBody Administrador administrador) {
        try {
            Administrador administradorActualizado = administradorUseCase.actualizarAdministrador(administrador);
            return ResponseEntity.ok("Administrador actualizado correctamente con ID: " + administradorActualizado.getId());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdministrador(@RequestParam String email, @RequestParam String password) {
        try {
            Administrador administrador = administradorUseCase.buscarPorEmail(email, password);
            return ResponseEntity.ok("Login exitoso. Bienvenido, " + administrador.getNombre() + ".");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmarAdministrador(@RequestParam String token) {
        try {
            Administrador administrador = administradorUseCase.buscarPorToken(token);
            administrador.setActivo(true);
            administrador.setTokenConfirmacion(null);
            administradorUseCase.actualizarAdministrador(administrador);
            return ResponseEntity.ok("Administrador confirmado correctamente. Ahora puedes iniciar sesión.");
        } catch (Exception e) {

            return ResponseEntity.ok(e.getMessage());
        }
    }

}
