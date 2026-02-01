
package com.luiza.simulador.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record SimulacaoRequest(
        @NotNull(message = "idade é obrigatória")
        @Min(value = 18, message = "idade mínima é 18")
        @Max(value = 70, message = "idade máxima é 70")
        Integer idade,

        @NotNull(message = "rendaMensal é obrigatória")
        @DecimalMin(value = "1000.00", inclusive = true, message = "rendaMensal mínima é 1000.00")
        BigDecimal rendaMensal,

        @NotNull(message = "valorSolicitado é obrigatório")
        @DecimalMin(value = "500.00", inclusive = true, message = "valorSolicitado mínimo é 500.00")
        @DecimalMax(value = "50000.00", inclusive = true, message = "valorSolicitado máximo é 50000.00")
        BigDecimal valorSolicitado,

        @NotNull(message = "numeroParcelas é obrigatório")
        @Min(value = 6, message = "numeroParcelas mínimo é 6")
        @Max(value = 48, message = "numeroParcelas máximo é 48")
        Integer numeroParcelas
) { }
