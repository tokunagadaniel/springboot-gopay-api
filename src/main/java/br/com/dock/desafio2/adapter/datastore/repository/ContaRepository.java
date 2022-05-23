package br.com.dock.desafio2.adapter.datastore.repository;


import br.com.dock.desafio2.adapter.datastore.entity.ContaEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContaRepository extends CassandraRepository<ContaEntity, UUID> {
}
