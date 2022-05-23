package br.com.dock.desafio2.application.service.conta;

import br.com.dock.desafio2.application.datastore.ContaDatastore;
import br.com.dock.desafio2.application.exception.BusinessException;
import br.com.dock.desafio2.domain.Conta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.dock.desafio2.application.messages.MessagesEnum.TK_DESAFIO2_00001I;
import static br.com.dock.desafio2.application.messages.MessagesEnum.TK_DESAFIO2_00002I;

@Service
@RequiredArgsConstructor
@Slf4j
public class BloqueiaContaServiceImpl implements BloqueiaContaService {

    private final ContaDatastore contaDatastore;

    private static final String CONST_SERVICE_DESCRIPTION = "que bloqueia a conta";

    public void process(String idConta) {
        log.info(TK_DESAFIO2_00001I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        Conta conta = contaDatastore.get(idConta, true).get();

        if (!conta.isFlagAtivo()) {
            throw new BusinessException("Acao nao permitida na conta");
        }
        
        conta.setFlagAtivo(false);

        contaDatastore.put(conta);

        log.info(TK_DESAFIO2_00002I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);
    }
}
