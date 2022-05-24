package br.com.tokunaga.application.service.transacao;

import br.com.tokunaga.domain.Transacao;

public interface RealizaSaqueService {

    void process(Transacao transacao);

}
