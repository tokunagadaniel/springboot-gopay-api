package br.com.tokunaga.application.service.transacao;

import br.com.tokunaga.domain.Transacao;

public interface RealizaDepositoService {
    void process(Transacao transacao);
}
