package com.prestamigos.prestamo.infraestructure.driver_adapters.external_repository;

import com.prestamigos.prestamo.domain.model.gateway.VendedorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class VendedorGatewayImpl implements VendedorGateway {

    private final RestTemplate restTemplate;

    @Override
    public boolean existeVendedor(Long vendedorId) {
        try {
            String url = "http://localhost:7000/api/vendedores/" + vendedorId;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error comunicando con microservicio usuario/vendedor");
        }
    }
}
