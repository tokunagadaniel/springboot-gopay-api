package br.com.tokunaga.adapter.datastore;

import br.com.tokunaga.domain.Pessoa;

import java.util.Optional;

public interface PessoaDatastore {
    void put(Pessoa data);

    Optional<Pessoa> get(String id, boolean throwException);
}
