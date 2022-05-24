package br.com.tokunaga.adapter.api.controller;

import br.com.tokunaga.application.service.conta.BloqueiaContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class BloqueiaContaController {

    private final BloqueiaContaService bloqueiaContaService;

    @PatchMapping("/block/{id_conta}")
    public ResponseEntity bloqueiaConta(final @PathVariable("id_conta") String idConta) {
        bloqueiaContaService.process(idConta);

        return new ResponseEntity(HttpStatus.OK);
    }
}
