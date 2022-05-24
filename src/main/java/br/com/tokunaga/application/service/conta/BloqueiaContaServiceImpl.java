package br.com.tokunaga.application.service.conta;

import br.com.tokunaga.application.datastore.ContaDatastore;
import br.com.tokunaga.application.exception.BusinessException;
import br.com.tokunaga.domain.Conta;
import br.com.tokunaga.application.messages.MessagesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BloqueiaContaServiceImpl implements BloqueiaContaService {

    private final ContaDatastore contaDatastore;

    private static final String CONST_SERVICE_DESCRIPTION = "que bloqueia a conta";

    public void process(String idConta) {
        log.info(MessagesEnum.TK_DESAFIO2_00001I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        Conta conta = contaDatastore.get(idConta, true).get();

        if (!conta.isFlagAtivo()) {
            throw new BusinessException("Acao nao permitida na conta");
        }
        
        conta.setFlagAtivo(false);

        contaDatastore.put(conta);

        log.info(MessagesEnum.TK_DESAFIO2_00002I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);
    }
}
