package br.com.dock.desafio2.application.service.transacao;

import br.com.dock.desafio2.domain.Transacao;

public interface RealizaDepositoService {
    void process(Transacao transacao);
}
