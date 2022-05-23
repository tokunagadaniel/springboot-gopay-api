package br.com.dock.desafio2.adapter.datastore;

import br.com.dock.desafio2.adapter.datastore.entity.ContaEntity;
import br.com.dock.desafio2.adapter.datastore.mapper.DatastoreMapper;
import br.com.dock.desafio2.adapter.datastore.repository.ContaRepository;
import br.com.dock.desafio2.application.datastore.ContaDatastore;
import br.com.dock.desafio2.application.exception.BusinessException;
import br.com.dock.desafio2.application.exception.DatastoreException;
import br.com.dock.desafio2.domain.Conta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static br.com.dock.desafio2.application.messages.MessagesEnum.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContaDatastoreImpl implements ContaDatastore {
    private final DatastoreMapper<Conta, ContaEntity> mapper;
    private final ContaRepository repository;

    private static final String CONST_MODEL = "Conta";

    @Override
    public void put(Conta data) {
        ContaEntity entity = mapper.mapToDatabase(data);

        try {
            repository.save(entity);
        } catch (Exception ex) {
            throw new DatastoreException(TK_DESAFIO2_00007E.getCodAndDescription(), ex);
        }

        log.info(TK_DESAFIO2_00008I.getCodAndDescription(), CONST_MODEL, data);
    }

    @Override
    public Optional<Conta> get(String id, boolean throwException) {
        try {
            Optional<ContaEntity> optionalEntity = repository.findById(UUID.fromString(id));

            if (optionalEntity.isPresent()) {

                return optionalEntity
                        .map(mapper::mapFromDatabase);
            }
        } catch (Exception ex) {
            throw new DatastoreException(TK_DESAFIO2_00009E.getCodAndDescription(), ex);
        }

        if (throwException) {
            throw new BusinessException(String.format(TK_DESAFIO2_00010E.getCodAndDescription(), CONST_MODEL, id));
        }

        return Optional.empty();
    }
}
