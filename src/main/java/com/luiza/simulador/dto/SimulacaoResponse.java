
package com.luiza.simulador.dto;

import java.math.BigDecimal;
import java.util.List;

public record SimulacaoResponse(
        BigDecimal taxaBasePercentual,
        java.util.List<Ajuste> ajustesAplicados,
        BigDecimal taxaFinalPercentual,
        BigDecimal valorParcela,
        BigDecimal valorTotal
) { }
