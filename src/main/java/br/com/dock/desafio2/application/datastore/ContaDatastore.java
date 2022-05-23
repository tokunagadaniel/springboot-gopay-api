package br.com.dock.desafio2.application.datastore;

import br.com.dock.desafio2.domain.Conta;

import java.util.Optional;

public interface ContaDatastore {
    void put(Conta data);

    Optional<Conta> get(String id, boolean throwsException);
}
