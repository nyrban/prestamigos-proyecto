package com.prestamigos.usuario.application;

import com.prestamigos.usuario.domain.model.gateway.AdministradorGateway;
import com.prestamigos.usuario.domain.model.gateway.ClienteGateway;
import com.prestamigos.usuario.domain.model.gateway.EncrypterGateway;
import com.prestamigos.usuario.domain.model.gateway.VendedorGateway;
import com.prestamigos.usuario.domain.usecase.AdministradorUseCase;
import com.prestamigos.usuario.domain.usecase.ClienteUseCase;
import com.prestamigos.usuario.domain.usecase.CorreoService;
import com.prestamigos.usuario.domain.usecase.VendedorUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ClienteUseCase clienteUseCase(
            ClienteGateway clienteGateway,
            EncrypterGateway encrypterGateway,
            CorreoService correoService,
            VendedorGateway vendedorGateway
    ) {
        return new ClienteUseCase(clienteGateway, encrypterGateway, correoService, vendedorGateway);
    }

    @Bean
    public VendedorUseCase vendedorUseCase(
            VendedorGateway vendedorGateway,
            EncrypterGateway encrypterGateway,
            CorreoService correoService
    ) {
        return new VendedorUseCase(vendedorGateway, encrypterGateway, correoService);
    }

    @Bean
    public AdministradorUseCase administradorUseCase(
            AdministradorGateway administradorGateway,
            EncrypterGateway encrypterGateway,
            CorreoService correoService
    ) {
        return new AdministradorUseCase(administradorGateway, encrypterGateway, correoService);
    }
}

