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
public class CriaContaServiceImpl implements CriaContaService {
    private final ContaDatastore contaDatastore;

    private static final String CONST_SERVICE_DESCRIPTION = "que abre a conta";

    public void process(Conta conta) {
        log.info(MessagesEnum.TK_DESAFIO2_00001I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        if (contaDatastore.get(conta.getIdConta(), false).isPresent()) {
            throw new BusinessException(String.format("Conta %s ja existente no banco", conta.getIdConta()));
        }

        contaDatastore.put(conta);

        log.info(MessagesEnum.TK_DESAFIO2_00002I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);
    }
}
