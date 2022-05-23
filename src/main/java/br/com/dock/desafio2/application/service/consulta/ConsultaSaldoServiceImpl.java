package br.com.dock.desafio2.application.service.consulta;

import br.com.dock.desafio2.application.datastore.ContaDatastore;
import br.com.dock.desafio2.domain.Conta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static br.com.dock.desafio2.application.messages.MessagesEnum.TK_DESAFIO2_00001I;
import static br.com.dock.desafio2.application.messages.MessagesEnum.TK_DESAFIO2_00002I;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaSaldoServiceImpl implements ConsultaSaldoService {

    private final ContaDatastore contaDatastore;

    private static final String CONST_SERVICE_DESCRIPTION = "que consulta o saldo em conta";

    @Override
    public double process(String idConta) {
        log.info(TK_DESAFIO2_00001I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        Conta conta = contaDatastore.get(idConta, true).get();

        log.info(TK_DESAFIO2_00002I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        return conta.getSaldo();
    }
}
