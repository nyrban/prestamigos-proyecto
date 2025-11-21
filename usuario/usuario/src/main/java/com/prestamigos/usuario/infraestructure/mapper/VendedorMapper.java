package com.prestamigos.usuario.infraestructure.mapper;

import com.prestamigos.usuario.domain.model.Vendedor;
import com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.Vendedor.VendedorData;
import org.springframework.stereotype.Component;

@Component
public class VendedorMapper {

    public Vendedor toVendedor(VendedorData data) {
        if (data == null) return null;

        Vendedor vendedor = new Vendedor();
        vendedor.setId(data.getId());
        vendedor.setEmail(data.getEmail());
        vendedor.setPassword(data.getPassword());
        vendedor.setNombre(data.getNombre());
        vendedor.setFechaNacimiento(data.getFechaNacimiento());
        vendedor.setDireccion(data.getDireccion());
        vendedor.setCiudad(data.getCiudad());
        vendedor.setDepartamento(data.getDepartamento());
        vendedor.setTelefono(data.getTelefono());
        vendedor.setActivo(data.isActivo());
        vendedor.setTokenConfirmacion(data.getTokenConfirmacion());

        //Campos propios del vendedor
        vendedor.setCupoTotal(data.getCupoTotal());
        vendedor.setCupoDisponible(data.getCupoDisponible());
        vendedor.setPorcentajeComision(data.getPorcentajeComision());
        vendedor.setGananciasAcumuladas(data.getGananciasAcumuladas());

        return vendedor;
    }

    public VendedorData toData(Vendedor vendedor) {
        if (vendedor == null) return null;

        VendedorData data = new VendedorData();
        data.setId(vendedor.getId());
        data.setEmail(vendedor.getEmail());
        data.setPassword(vendedor.getPassword());
        data.setNombre(vendedor.getNombre());
        data.setFechaNacimiento(vendedor.getFechaNacimiento());
        data.setDireccion(vendedor.getDireccion());
        data.setCiudad(vendedor.getCiudad());
        data.setDepartamento(vendedor.getDepartamento());
        data.setTelefono(vendedor.getTelefono());
        data.setActivo(vendedor.getActivo());
        data.setTokenConfirmacion(vendedor.getTokenConfirmacion());

        // Campos propios del vendedor
        data.setCupoTotal(vendedor.getCupoTotal());
        data.setCupoDisponible(vendedor.getCupoDisponible());
        data.setPorcentajeComision(vendedor.getPorcentajeComision());
        data.setGananciasAcumuladas(vendedor.getGananciasAcumuladas());

        return data;
    }
}
