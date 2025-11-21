package com.prestamigos.prestamo.infraestructure.driver_adapters.external_repository;

import com.prestamigos.prestamo.domain.model.gateway.AdministradorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AdministradorGatewayImpl implements AdministradorGateway {

    private final RestTemplate restTemplate;
    private static final String URL = "http://localhost:8083/api/prestaamigos/administrador/{id}";

    @Override
    public boolean existeAdministrador(Long id) {
        try {
            restTemplate.getForObject(URL, Object.class, id);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error comunicando con microservicio usuario/administrador");
        }
    }
}
