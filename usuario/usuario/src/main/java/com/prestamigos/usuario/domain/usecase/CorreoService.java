package com.prestamigos.usuario.domain.usecase;

import com.prestamigos.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    public void enviarCorreoConfirmacion(Usuario usuario) {
        String tipoUsuario = usuario.getClass().getSimpleName().toLowerCase();
        String linkConfirmacion = "http://localhost:8083/api/prestaamigos/" + tipoUsuario + "/confirm?token="
                + usuario.getTokenConfirmacion();

        System.out.println("🔗 Enlace de confirmación (" + tipoUsuario + "): " + linkConfirmacion);
    }
}
