package br.com.dock.desafio2.adapter.datastore.mapper;

import br.com.dock.desafio2.adapter.datastore.entity.TransacaoEntity;
import br.com.dock.desafio2.adapter.datastore.utils.ConverteDataUtil;
import br.com.dock.desafio2.domain.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransacaoDatastoreMapper extends ConverteDataUtil implements DatastoreMapper<Transacao, TransacaoEntity> {

    @Override
    public TransacaoEntity mapToDatabase(Transacao transacao) {
        return TransacaoEntity.builder()
                .idConta(UUID.fromString(transacao.getIdConta()))
                .dataTransacao(transacao.getDataTransacao())
                .idTransacao(transacao.getIdTransacao())
                .valor(transacao.getValor())
                .build();
    }

    @Override
    public Transacao mapFromDatabase(TransacaoEntity transacaoEntity) {
        return Transacao.builder()
                .idConta(transacaoEntity.getIdConta().toString())
                .dataTransacao(transacaoEntity.getDataTransacao())
                .idTransacao(transacaoEntity.getIdTransacao())
                .valor(transacaoEntity.getValor())
                .build();
    }
}
