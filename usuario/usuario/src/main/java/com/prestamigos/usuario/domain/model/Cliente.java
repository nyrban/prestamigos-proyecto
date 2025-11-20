package com.prestamigos.usuario.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class Cliente extends Usuario {

    private Long VendedorId;
}