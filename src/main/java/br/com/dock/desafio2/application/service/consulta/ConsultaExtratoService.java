package br.com.dock.desafio2.application.service.consulta;

import br.com.dock.desafio2.domain.Transacao;

import java.util.List;

public interface ConsultaExtratoService {
    List<Transacao> process(String idConta, String dataInicio, String dataFim);
}
