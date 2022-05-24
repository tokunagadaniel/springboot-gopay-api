package br.com.tokunaga.application.service.consulta;

import br.com.tokunaga.domain.Transacao;

import java.util.List;

public interface ConsultaExtratoService {
    List<Transacao> process(String idConta, String dataInicio, String dataFim);
}
