package br.com.tokunaga.adapter.datastore.repository;


import br.com.tokunaga.adapter.datastore.entity.TransacaoEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends CassandraRepository<TransacaoEntity, UUID> {
    Iterable<TransacaoEntity> findByIdConta(UUID idConta);
    Iterable<TransacaoEntity> findByIdContaAndDataTransacaoBetween(UUID idConta, LocalDate dataTransacaoInicio, LocalDate dataTransacaoFim);
    Iterable<TransacaoEntity> findByIdContaAndDataTransacao(UUID idConta, LocalDate dataTransacao);
}
