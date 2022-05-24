package br.com.tokunaga.adapter.datastore;

import br.com.tokunaga.domain.Transacao;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoDatastore {
    void put(Transacao transacao);

    //void remove(String idConta, Long idTransacao);

    List<Transacao> get(String idConta);

    List<Transacao> get(String idConta, LocalDate date);

    List<Transacao> get(String idConta, String dataInicio, String dataFim);
}
