package br.com.tokunaga.adapter.datastore;

import br.com.tokunaga.adapter.datastore.entity.PessoaEntity;
import br.com.tokunaga.adapter.datastore.mapper.DatastoreMapper;
import br.com.tokunaga.adapter.datastore.repository.PessoaRepository;
import br.com.tokunaga.application.datastore.PessoaDatastore;
import br.com.tokunaga.application.exception.BusinessException;
import br.com.tokunaga.application.exception.DatastoreException;
import br.com.tokunaga.domain.Pessoa;
import br.com.tokunaga.application.messages.MessagesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PessoaDatastoreImpl implements PessoaDatastore {
    private final DatastoreMapper<Pessoa, PessoaEntity> mapper;
    private final PessoaRepository repository;

    private static final String CONST_MODEL = "Conta";

    @Override
    public void put(Pessoa pessoa) {
        PessoaEntity entity = mapper.mapToDatabase(pessoa);

        try {
            repository.save(entity);
        } catch (Exception ex) {
            throw new DatastoreException(MessagesEnum.TK_DESAFIO2_00007E.getCodAndDescription(), ex);
        }

        log.info(MessagesEnum.TK_DESAFIO2_00008I.getCodAndDescription(), CONST_MODEL, pessoa);
    }

    @Override
    public Optional<Pessoa> get(String id, boolean throwException) {
        try {
            Optional<PessoaEntity> optionalEntity = repository.findById(UUID.fromString(id));

            if (optionalEntity.isPresent()) {

                return optionalEntity
                        .map(mapper::mapFromDatabase);
            }
        } catch (Exception ex) {
            throw new DatastoreException(MessagesEnum.TK_DESAFIO2_00009E.getCodAndDescription(), ex);
        }

        if (throwException) {
            throw new BusinessException(String.format(MessagesEnum.TK_DESAFIO2_00010E.getCodAndDescription(), CONST_MODEL, id));
        }

        return Optional.empty();
    }
}
