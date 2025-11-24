package com.prestamigos.pago.application;

import com.prestamigos.pago.domain.model.gateway.ClienteGateway;
import com.prestamigos.pago.domain.model.gateway.PagoGateway;
import com.prestamigos.pago.domain.model.gateway.PrestamoGateway;
import com.prestamigos.pago.domain.usecase.PagoUseCase;
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
    public PagoUseCase pagoUseCase(
            PagoGateway pagoGateway,
            ClienteGateway clienteGateway,
            PrestamoGateway prestamoGateway
    ) {
        return new PagoUseCase(pagoGateway, clienteGateway, prestamoGateway);
    }
}
