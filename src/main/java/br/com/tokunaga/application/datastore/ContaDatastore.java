package br.com.tokunaga.application.datastore;

import br.com.tokunaga.domain.Conta;

import java.util.Optional;

public interface ContaDatastore {
    void put(Conta data);

    Optional<Conta> get(String id, boolean throwsException);
}
