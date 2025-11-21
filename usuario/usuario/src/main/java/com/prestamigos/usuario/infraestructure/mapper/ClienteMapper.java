package com.prestamigos.usuario.infraestructure.mapper;

import com.prestamigos.usuario.domain.model.Cliente;
import com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Cliente.ClienteData;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toCliente(ClienteData data) {
        if (data == null) return null;

        Cliente cliente = new Cliente();
        cliente.setId(data.getId());
        cliente.setVendedorId(data.getVendedorId());
        cliente.setEmail(data.getEmail());
        cliente.setPassword(data.getPassword());
        cliente.setNombre(data.getNombre());
        cliente.setFechaNacimiento(data.getFechaNacimiento());
        cliente.setDireccion(data.getDireccion());
        cliente.setCiudad(data.getCiudad());
        cliente.setDepartamento(data.getDepartamento());
        cliente.setTelefono(data.getTelefono());
        cliente.setActivo(data.isActivo());
        cliente.setTokenConfirmacion(data.getTokenConfirmacion());
        return cliente;
    }

    public ClienteData toData(Cliente cliente) {
        if (cliente == null) return null;

        ClienteData data = new ClienteData();
        data.setId(cliente.getId());
        data.setVendedorId(cliente.getVendedorId());
        data.setEmail(cliente.getEmail());
        data.setPassword(cliente.getPassword());
        data.setNombre(cliente.getNombre());
        data.setFechaNacimiento(cliente.getFechaNacimiento());
        data.setDireccion(cliente.getDireccion());
        data.setCiudad(cliente.getCiudad());
        data.setDepartamento(cliente.getDepartamento());
        data.setTelefono(cliente.getTelefono());
        data.setActivo(cliente.getActivo());
        data.setTokenConfirmacion(cliente.getTokenConfirmacion());
        return data;
    }
}
