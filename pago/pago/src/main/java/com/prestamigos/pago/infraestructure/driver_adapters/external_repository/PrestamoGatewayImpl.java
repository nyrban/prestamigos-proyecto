package com.prestamigos.pago.infraestructure.driver_adapters.external_repository;

import com.prestamigos.pago.domain.model.gateway.PrestamoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PrestamoGatewayImpl implements PrestamoGateway {

    private final RestTemplate restTemplate;
    private static final String URL = "http://localhost:7001/api/prestamigos/prestamos/{id}";

    @Override
    public boolean existePrestamo(Long id) {
        try {
            Object prestamo = restTemplate.getForObject(URL, Object.class, id);

            return prestamo != null; // existe
        }
        catch (HttpClientErrorException.NotFound e) {
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }


    @Override
    public boolean prestamoPerteneceAlCliente(Long prestamoId, Long clienteId) {
        try {
            Map<String, Object> prestamo = restTemplate.getForObject(URL, Map.class, prestamoId);

            if (prestamo == null) {
                return false;
            }

            Object clienteRealObj = prestamo.get("clienteId");
            if (clienteRealObj == null) {
                return false;
            }

            Long clienteReal = Long.valueOf(clienteRealObj.toString());
            return clienteReal.equals(clienteId);

        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}