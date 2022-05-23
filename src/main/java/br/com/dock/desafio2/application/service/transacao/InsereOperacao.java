package br.com.dock.desafio2.application.service.transacao;

import br.com.dock.desafio2.application.datastore.ContaDatastore;
import br.com.dock.desafio2.application.datastore.TransacaoDatastore;
import br.com.dock.desafio2.adapter.datastore.utils.ConverteDataUtil;
import br.com.dock.desafio2.domain.Conta;
import br.com.dock.desafio2.domain.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class InsereOperacao extends ConverteDataUtil {
    protected final TransacaoDatastore transacaoDatastore;
    protected final ContaDatastore contaDatastore;

    @Transactional
    protected void atualizaSaldoETransacao(Conta conta, Transacao transacao) {
        transacaoDatastore.put(transacao);
        contaDatastore.put(conta);
    }

    protected void inicializaIdTransacao(List<Transacao> list, Transacao transacao) {
        long value = list.stream()
                .mapToLong(Transacao::getIdTransacao)
                .max()
                .orElse(0) + 1;

        transacao.setIdTransacao(value);
    }
}
