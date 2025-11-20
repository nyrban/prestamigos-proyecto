package com.prestamigos.usuario.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class Vendedor extends Usuario {

    private Double gananciasAcumuladas = 0.0;
    private Double cupoTotal = 0.0;
    private Double cupoDisponible = 0.0;
    private Double porcentajeComision = 0.0;

}