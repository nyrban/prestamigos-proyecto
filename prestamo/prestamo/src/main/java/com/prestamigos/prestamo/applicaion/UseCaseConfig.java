package com.prestamigos.prestamo.applicaion;

import com.prestamigos.prestamo.domain.model.gateway.AdministradorGateway;
import com.prestamigos.prestamo.domain.model.gateway.ClienteGateway;
import com.prestamigos.prestamo.domain.model.gateway.PrestamoGateway;
import com.prestamigos.prestamo.domain.model.gateway.SolicitudGateway;
import com.prestamigos.prestamo.domain.usecase.PrestamoUseCase;
import com.prestamigos.prestamo.domain.usecase.SolicitudUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UseCaseConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SolicitudUseCase solicitudUseCase(
            SolicitudGateway solicitudGateway,
            ClienteGateway clienteGateway,
            AdministradorGateway administradorGateway
    ) {
        return new SolicitudUseCase(
                solicitudGateway,
                clienteGateway,
                administradorGateway
        );
    }

    @Bean
    public PrestamoUseCase prestamoUseCase(
            PrestamoGateway prestamoGateway,
            ClienteGateway clienteGateway
    ) {
        return new PrestamoUseCase(
                prestamoGateway,
                clienteGateway
        );
    }
}