package com.prestamigos.prestamo.infraestructure.driver_adapters.external_repository;

import com.prestamigos.prestamo.domain.model.gateway.ClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final RestTemplate restTemplate;

    @Override
    public boolean existeCliente(Long clienteId) {
        try {
            String url = "http://localhost:7000/api/prestamigos/cliente/" + clienteId;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

