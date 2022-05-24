package br.com.tokunaga.adapter.datastore.mapper;

import br.com.tokunaga.adapter.datastore.entity.TransacaoEntity;
import br.com.tokunaga.adapter.datastore.utils.ConverteDataUtil;
import br.com.tokunaga.domain.Transacao;
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
