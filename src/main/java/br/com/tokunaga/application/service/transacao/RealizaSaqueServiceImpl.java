package br.com.tokunaga.application.service.transacao;

import br.com.tokunaga.application.datastore.ContaDatastore;
import br.com.tokunaga.application.datastore.TransacaoDatastore;
import br.com.tokunaga.application.exception.BusinessException;
import br.com.tokunaga.domain.Conta;
import br.com.tokunaga.domain.Transacao;
import br.com.tokunaga.application.messages.MessagesEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class RealizaSaqueServiceImpl extends InsereOperacao implements RealizaSaqueService {

    public RealizaSaqueServiceImpl(TransacaoDatastore transacaoDatastore, ContaDatastore contaDatastore) {
        super(transacaoDatastore, contaDatastore);
    }

    private static final String CONST_DESCRICAO_SERVICE = "de saque de valores";

    @Override
    public void process(Transacao transacao) {
        log.info(MessagesEnum.TK_DESAFIO2_00001I.getCodAndDescription() + CONST_DESCRICAO_SERVICE);

        Transacao transacaoAtual = transacao;
        double valorSaque = transacao.getValor();

        if (valorSaque > 0) {
            transacaoAtual.setValor(valorSaque * -1);
        }

        final String idConta = transacao.getIdConta();

        List<Transacao> transacaoList = transacaoDatastore.get(idConta, LocalDate.now());

        double somaSaquesDia = transacaoList.stream()
                .filter(t -> t.getValor() < 0)
                .mapToDouble(t -> t.getValor())
                .reduce(0, (a, b) -> a + b);

        inicializaIdTransacao(transacaoList, transacao);

        Conta conta = contaDatastore
                .get(idConta, true)
                .get();

        double saldo = conta.getSaldo();
        double limiteDisponivel = conta.getLimiteSaqueDiario() + somaSaquesDia;

        aplicaConsistencias(conta, valorSaque, limiteDisponivel);

        saldo += valorSaque;
        conta.setSaldo(saldo);

        super.atualizaSaldoETransacao(conta, transacao);

        log.info(MessagesEnum.TK_DESAFIO2_00002I.getCodAndDescription() + CONST_DESCRICAO_SERVICE);
    }

    private void aplicaConsistencias(Conta conta, double valorSaque, double limiteDisponivel) {
        if (valorSaque == 0) {
            throw new BusinessException(MessagesEnum.TK_DESAFIO2_00003E.getCodAndDescription());
        }

        if (conta.getSaldo() < valorSaque) {
            throw new BusinessException(MessagesEnum.TK_DESAFIO2_00004E.getCodAndDescription());
        }

        if (limiteDisponivel < valorSaque) {
            throw new BusinessException(MessagesEnum.TK_DESAFIO2_00005E.getCodAndDescription());
        }

        if (!conta.isFlagAtivo()) {
            throw new BusinessException(MessagesEnum.TK_DESAFIO2_00006E.getCodAndDescription());
        }
    }
}
