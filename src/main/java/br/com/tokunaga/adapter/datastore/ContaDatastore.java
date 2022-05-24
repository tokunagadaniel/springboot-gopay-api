package br.com.tokunaga.adapter.datastore;

import br.com.tokunaga.domain.Conta;

import java.util.Optional;

public interface ContaDatastore {
    void put(Conta data);

    Optional<Conta> get(String id, boolean throwsException);
}
