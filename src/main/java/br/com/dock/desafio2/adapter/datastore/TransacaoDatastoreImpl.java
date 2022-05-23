package br.com.dock.desafio2.adapter.datastore;

import br.com.dock.desafio2.adapter.datastore.entity.TransacaoEntity;
import br.com.dock.desafio2.adapter.datastore.mapper.DatastoreMapper;
import br.com.dock.desafio2.adapter.datastore.repository.TransacaoRepository;
import br.com.dock.desafio2.adapter.datastore.utils.ConverteDataUtil;
import br.com.dock.desafio2.application.datastore.TransacaoDatastore;
import br.com.dock.desafio2.application.exception.DatastoreException;
import br.com.dock.desafio2.domain.Transacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static br.com.dock.desafio2.application.messages.MessagesEnum.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransacaoDatastoreImpl extends ConverteDataUtil implements TransacaoDatastore {
    private final DatastoreMapper<Transacao, TransacaoEntity> mapper;
    private final TransacaoRepository repository;

    private static final String CONST_MODEL = "Transacao";

    @Override
    public void put(Transacao transacao) {
        try {
            repository.save(mapper.mapToDatabase(transacao));
        } catch (Exception ex) {
            throw new DatastoreException(TK_DESAFIO2_00007E.getCodAndDescription(), ex);
        }

        log.info(TK_DESAFIO2_00008I.getCodAndDescription(), CONST_MODEL, transacao);
    }

    @Override
    public List<Transacao> get(String idConta) {
        Iterable<TransacaoEntity> entityIterable;

        try {
            entityIterable = repository.findByIdConta(UUID.fromString(idConta));
            return initializeTransactions(entityIterable);
        } catch (Exception ex) {
            throw new DatastoreException(TK_DESAFIO2_00009E.getCodAndDescription(), ex);
        }
    }

    @Override
    public List<Transacao> get(String idConta, LocalDate date) {
        try {
            Iterable<TransacaoEntity> entityIterable = repository.findByIdContaAndDataTransacao(
                    UUID.fromString(idConta),
                    date
            );

            return initializeTransactions(entityIterable);
        } catch (Exception ex) {
            throw new DatastoreException(TK_DESAFIO2_00009E.getCodAndDescription(), ex);
        }
    }

    @Override
    public List<Transacao> get(String idConta, String dataInicio, String dataFim) {
        Iterable<TransacaoEntity> entityIterable;

        try {
            LocalDate d1 = super.convertToLocalDate(dataInicio).minusDays(1);
            LocalDate d2 = super.convertToLocalDate(dataFim).plusDays(1);

            entityIterable = repository.findByIdContaAndDataTransacaoBetween(UUID.fromString(idConta), d1, d2);
            return initializeTransactions(entityIterable);
        } catch (Exception ex) {
            throw new DatastoreException(TK_DESAFIO2_00009E.getCodAndDescription(), ex);
        }
    }

    private List<Transacao> initializeTransactions(Iterable<TransacaoEntity> entityIterable) {
        List<Transacao> list = new LinkedList<>();

        entityIterable.forEach(p -> {
            Transacao t = mapper.mapFromDatabase(p);
            list.add(t);
        });

        return list;
    }
}
