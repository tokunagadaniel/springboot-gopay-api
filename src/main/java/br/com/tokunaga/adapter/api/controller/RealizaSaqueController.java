package br.com.tokunaga.adapter.api.controller;

import br.com.tokunaga.adapter.api.dto.TransacaoDto;
import br.com.tokunaga.application.service.transacao.RealizaSaqueService;
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
