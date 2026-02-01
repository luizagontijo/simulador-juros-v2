
package com.luiza.simulador.controller;

import com.luiza.simulador.dto.SimulacaoRequest;
import com.luiza.simulador.dto.SimulacaoResponse;
import com.luiza.simulador.service.SimulacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulacoes")
public class SimulacaoController {

    private final SimulacaoService service;

    public SimulacaoController(SimulacaoService service) {
        this.service = service;
    }

    @PostMapping("/juros")
    @ResponseStatus(HttpStatus.OK)
    public SimulacaoResponse simular(@RequestBody @Valid SimulacaoRequest request) {
        return service.simular(request);
    }
}
