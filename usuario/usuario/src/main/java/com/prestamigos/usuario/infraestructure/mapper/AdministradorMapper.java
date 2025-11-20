package com.prestamigos.usuario.infraestructure.mapper;

import com.prestamigos.usuario.domain.model.Administrador;
import com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.AdministradorData;
import org.springframework.stereotype.Component;

@Component
public class AdministradorMapper {

    public Administrador toAdministrador(AdministradorData data) {
        if (data == null) return null;

        Administrador admin = new Administrador();
        admin.setId(data.getId());
        admin.setEmail(data.getEmail());
        admin.setPassword(data.getPassword());
        admin.setNombre(data.getNombre());
        admin.setFechaNacimiento(data.getFechaNacimiento());
        admin.setDireccion(data.getDireccion());
        admin.setCiudad(data.getCiudad());
        admin.setDepartamento(data.getDepartamento());
        admin.setTelefono(data.getTelefono());
        admin.setActivo(data.isActivo());
        admin.setTokenConfirmacion(data.getTokenConfirmacion());
        return admin;
    }

    public AdministradorData toData(Administrador admin) {
        if (admin == null) return null;

        AdministradorData data = new AdministradorData();
        data.setId(admin.getId());
        data.setEmail(admin.getEmail());
        data.setPassword(admin.getPassword());
        data.setNombre(admin.getNombre());
        data.setFechaNacimiento(admin.getFechaNacimiento());
        data.setDireccion(admin.getDireccion());
        data.setCiudad(admin.getCiudad());
        data.setDepartamento(admin.getDepartamento());
        data.setTelefono(admin.getTelefono());
        data.setActivo(admin.getActivo());
        data.setTokenConfirmacion(admin.getTokenConfirmacion());
        return data;
    }
}
