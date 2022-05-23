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
public class CriaContaServiceImpl implements CriaContaService {
    private final ContaDatastore contaDatastore;

    private static final String CONST_SERVICE_DESCRIPTION = "que abre a conta";

    public void process(Conta conta) {
        log.info(TK_DESAFIO2_00001I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);

        if (contaDatastore.get(conta.getIdConta(), false).isPresent()) {
            throw new BusinessException(String.format("Conta %s ja existente no banco", conta.getIdConta()));
        }

        contaDatastore.put(conta);

        log.info(TK_DESAFIO2_00002I.getCodAndDescription() + CONST_SERVICE_DESCRIPTION);
    }
}
