package br.com.tokunaga.application.service.transacao;

import br.com.tokunaga.application.datastore.ContaDatastore;
import br.com.tokunaga.application.datastore.TransacaoDatastore;
import br.com.tokunaga.adapter.datastore.utils.ConverteDataUtil;
import br.com.tokunaga.domain.Conta;
import br.com.tokunaga.domain.Transacao;
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
