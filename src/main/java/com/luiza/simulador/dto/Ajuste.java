
package com.luiza.simulador.dto;

import java.math.BigDecimal;

public record Ajuste(
        String regra,
        BigDecimal incrementoPercentual
) { }
