package br.com.tokunaga.adapter.api.controller;

import br.com.tokunaga.adapter.api.dto.TransacaoDto;
import br.com.tokunaga.application.service.transacao.RealizaDepositoService;
import br.com.tokunaga.domain.Transacao;
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
