package br.com.dock.desafio2.adapter.api.controller;

import br.com.dock.desafio2.adapter.datastore.utils.ConverteDataUtil;
import br.com.dock.desafio2.application.service.consulta.ConsultaExtratoService;
import br.com.dock.desafio2.domain.Transacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Component
@RestController
@RequestMapping("/consulta")
@RequiredArgsConstructor
public class ConsultaExtratoController {

    private final ConsultaExtratoService consultaExtratoService;
    private final ObjectMapper mapper;

    @SneakyThrows
    @GetMapping("/extrato")
    public ResponseEntity<String> obtemExtrato(final @RequestParam(name = "id_conta") String idConta,
                                               final @RequestParam(name = "data_inicio", required = false) String dataInicio,
                                               final @RequestParam(name = "data_fim", required = false) String dataFim) {
        List<Transacao> transacoes = consultaExtratoService.process(idConta, dataInicio, dataFim);
        if (transacoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(EMPTY);
        }

        return ResponseEntity.ok(mapper.writeValueAsString(transacoes));
    }
}
