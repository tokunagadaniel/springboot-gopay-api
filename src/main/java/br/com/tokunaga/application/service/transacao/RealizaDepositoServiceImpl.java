package br.com.tokunaga.application.service.transacao;

import br.com.tokunaga.application.datastore.ContaDatastore;
import br.com.tokunaga.application.datastore.TransacaoDatastore;
import br.com.tokunaga.application.exception.BusinessException;
import br.com.tokunaga.domain.Conta;
import br.com.tokunaga.domain.Transacao;
import br.com.tokunaga.application.messages.MessagesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RealizaDepositoServiceImpl extends InsereOperacao implements RealizaDepositoService {

    private static final String CONST_DESCRICAO_SERVICE = "de deposito de valores";
    private static final String CONST_VALOR_INVALIDO = "Valor do deposito invalido %s";

    public RealizaDepositoServiceImpl(TransacaoDatastore transacaoDatastore, ContaDatastore contaDatastore) {
        super(transacaoDatastore, contaDatastore);
    }

    @Override
    public void process(Transacao transacao) {
        log.info(MessagesEnum.TK_DESAFIO2_00001I.getCodAndDescription() + CONST_DESCRICAO_SERVICE);

        double valor = transacao.getValor();

        if (valor <= 0) {
            throw new BusinessException(String.format(CONST_VALOR_INVALIDO, valor));
        }

        final String idConta = transacao.getIdConta();

        Conta conta = contaDatastore.get(idConta, true).get();

        List<Transacao> transacaoList = transacaoDatastore.get(idConta, transacao.getDataTransacao());

        inicializaIdTransacao(transacaoList, transacao);

        double saldo = conta.getSaldo() + valor;
        conta.setSaldo(saldo);

        super.atualizaSaldoETransacao(conta, transacao);

        log.info(MessagesEnum.TK_DESAFIO2_00002I.getCodAndDescription() + CONST_DESCRICAO_SERVICE);
    }
}
