package br.com.dock.desafio2.adapter.api.controller;

import br.com.dock.desafio2.adapter.api.dto.TransacaoDto;
import br.com.dock.desafio2.application.service.transacao.RealizaDepositoService;
import br.com.dock.desafio2.domain.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Component
@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class RealizaDepositoController {

    private final RealizaDepositoService realizaDepositoService;

    @PostMapping("/deposita")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deposita(final @RequestBody TransacaoDto transacaoDto) {
        Transacao transacao = Transacao.builder()
                .idConta(transacaoDto.getIdConta())
                .valor(transacaoDto.getValor())
                .dataTransacao(LocalDate.now())
                .build();

        realizaDepositoService.process(transacao);
    }
}
