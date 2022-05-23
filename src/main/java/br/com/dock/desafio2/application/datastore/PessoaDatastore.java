package br.com.dock.desafio2.application.datastore;

import br.com.dock.desafio2.domain.Pessoa;

import java.util.Optional;

public interface PessoaDatastore {
    void put(Pessoa data);

    Optional<Pessoa> get(String id, boolean throwException);
}
