package br.com.tokunaga.adapter.api.controller;

import br.com.tokunaga.application.service.consulta.ConsultaSaldoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/consulta")
@RequiredArgsConstructor
public class ConsultaSaldoController {

    private final ConsultaSaldoService consultaSaldoService;

    @GetMapping("/saldo/{id_conta}")
    public ResponseEntity<String> obtemSaldo(final @PathVariable("id_conta") String idConta) {
        double saldo = consultaSaldoService.process(idConta);

        return ResponseEntity.ok(String.format("R$ %,.2f", saldo));
    }
}
