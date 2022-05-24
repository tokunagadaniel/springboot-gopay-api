package br.com.tokunaga.application.service.consulta;

import br.com.tokunaga.application.datastore.ContaDatastore;
import br.com.tokunaga.domain.Conta;
import br.com.tokunaga.application.messages.MessagesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaSaldoServiceImpl implements ConsultaSaldoService {

    private final ContaDatastore contaDatastore;

    private static final String CONST_SERVICE_DESCRIPTION = "que consulta o saldo em conta";

    @Override
    public double process(String idConta) {
        log.info(MessagesEnum.TK_DESAFIO2_00001I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        Conta conta = contaDatastore.get(idConta, true).get();

        log.info(MessagesEnum.TK_DESAFIO2_00002I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        return conta.getSaldo();
    }
}
