
package com.luiza.simulador.service;

import com.luiza.simulador.dto.Ajuste;
import com.luiza.simulador.dto.SimulacaoRequest;
import com.luiza.simulador.dto.SimulacaoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimulacaoService {

    // Base: 2.0% a.m.
    private static final BigDecimal BASE_PERCENT = new BigDecimal("2.0");
    private static final MathContext MC = new MathContext(12, RoundingMode.HALF_UP);

    public SimulacaoResponse simular(SimulacaoRequest req) {
        // Regra adicional: idade > 60 => máximo 24 parcelas
        if (req.idade() > 60 && req.numeroParcelas() > 24) {
            throw new BusinessException("Para idade acima de 60 anos, o número máximo de parcelas é 24.");
        }

        List<Ajuste> ajustes = new ArrayList<>();
        BigDecimal totalAjustes = BigDecimal.ZERO;

        // idade > 60 aumenta 0,5%
        if (req.idade() > 60) {
            totalAjustes = totalAjustes.add(new BigDecimal("0.5"));
            ajustes.add(new Ajuste("idade > 60", new BigDecimal("0.5")));
        }

        // renda mensal < 2000 aumenta 1,0%
        if (req.rendaMensal().compareTo(new BigDecimal("2000.00")) < 0) {
            totalAjustes = totalAjustes.add(new BigDecimal("1.0"));
            ajustes.add(new Ajuste("rendaMensal < 2000", new BigDecimal("1.0")));
        }

        // valor solicitado > 30000 aumenta 1,5%
        if (req.valorSolicitado().compareTo(new BigDecimal("30000.00")) > 0) {
            totalAjustes = totalAjustes.add(new BigDecimal("1.5"));
            ajustes.add(new Ajuste("valorSolicitado > 30000", new BigDecimal("1.5")));
        }

        // numeroParcelas >= 36 aumenta 1,0%
        if (req.numeroParcelas() >= 36) {
            totalAjustes = totalAjustes.add(new BigDecimal("1.0"));
            ajustes.add(new Ajuste("numeroParcelas >= 36", new BigDecimal("1.0")));
        }

        BigDecimal taxaFinalPercentual = BASE_PERCENT.add(totalAjustes);

        // Conversão de percentual para taxa decimal mensal
        BigDecimal i = taxaFinalPercentual.divide(new BigDecimal("100"), MC);

        // Fórmula PRICE (PMT): P * i / (1 - (1+i)^-n)
        BigDecimal P = req.valorSolicitado();
        int n = req.numeroParcelas();

        // (1 + i)^n
        BigDecimal umMaisI = BigDecimal.ONE.add(i, MC);
        BigDecimal umMaisIpotenciaN = umMaisI.pow(n, MC);

        BigDecimal denominador = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(umMaisIpotenciaN, MC), MC);

        BigDecimal parcela = P.multiply(i, MC).divide(denominador, MC);
        BigDecimal parcela2dec = parcela.setScale(2, RoundingMode.HALF_UP);

        BigDecimal valorTotal = parcela2dec.multiply(new BigDecimal(n), MC).setScale(2, RoundingMode.HALF_UP);

        return new SimulacaoResponse(
                BASE_PERCENT.setScale(2, RoundingMode.HALF_UP),
                ajustes,
                taxaFinalPercentual.setScale(2, RoundingMode.HALF_UP),
                parcela2dec,
                valorTotal
        );
    }
}
