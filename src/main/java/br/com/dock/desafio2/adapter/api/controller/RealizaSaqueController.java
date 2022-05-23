package br.com.dock.desafio2.adapter.api.controller;

import br.com.dock.desafio2.adapter.api.dto.TransacaoDto;
import br.com.dock.desafio2.application.service.transacao.RealizaSaqueService;
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
public class RealizaSaqueController {

    private final RealizaSaqueService realizaSaqueService;

    @PostMapping("/saque")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sacaValor(final @RequestBody TransacaoDto transacaoDto) {
        Transacao transacao = Transacao.builder()
                .idConta(transacaoDto.getIdConta())
                .valor(transacaoDto.getValor())
                .dataTransacao(LocalDate.now())
                .build();

        realizaSaqueService.process(transacao);
    }
}
