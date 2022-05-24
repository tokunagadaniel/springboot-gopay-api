package br.com.tokunaga.adapter.datastore.repository;


import br.com.tokunaga.adapter.datastore.entity.ContaEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContaRepository extends CassandraRepository<ContaEntity, UUID> {
}
