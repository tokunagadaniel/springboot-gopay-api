package br.com.tokunaga.adapter.api.controller;

import br.com.tokunaga.adapter.api.dto.ContaDto;
import br.com.tokunaga.application.service.conta.CriaContaService;
import br.com.tokunaga.domain.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Component
@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class CriaContaController {

    private final CriaContaService criaContaService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void criaConta(final @RequestBody ContaDto contaDto) {
        Conta conta = Conta.builder()
                .idConta(contaDto.getIdConta())
                .idPessoa(contaDto.getIdPessoa())
                .limiteSaqueDiario(contaDto.getLimiteSaqueDiario())
                .tipoConta(contaDto.getTipoConta())
                .saldo(0)
                .flagAtivo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        criaContaService.process(conta);
    }
}
