package br.com.dock.desafio2.adapter.datastore.mapper;

import br.com.dock.desafio2.adapter.datastore.entity.ContaEntity;
import br.com.dock.desafio2.adapter.datastore.utils.ConverteDataUtil;
import br.com.dock.desafio2.domain.Conta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.fasterxml.uuid.Generators.nameBasedGenerator;

@Component
@RequiredArgsConstructor
public class ContaDatastoreMapper extends ConverteDataUtil implements DatastoreMapper<Conta, ContaEntity> {

    @Override
    public ContaEntity mapToDatabase(Conta conta) {
        return ContaEntity.builder()
                .idConta(UUID.fromString(conta.getIdConta()))
                .idPessoa(nameBasedGenerator().generate(conta.getIdPessoa()))
                .saldo(conta.getSaldo())
                .limiteSaqueDiario(conta.getLimiteSaqueDiario())
                .flagAtivo(conta.isFlagAtivo())
                .tipoConta(conta.getTipoConta())
                .dataCriacao(conta.getDataCriacao())
                .build();
    }

    @Override
    public Conta mapFromDatabase(ContaEntity contaEntity) {
        return Conta.builder()
                .idConta(contaEntity.getIdConta().toString())
                .idPessoa(contaEntity.getIdPessoa().toString())
                .saldo(contaEntity.getSaldo())
                .limiteSaqueDiario(contaEntity.getLimiteSaqueDiario())
                .flagAtivo(contaEntity.isFlagAtivo())
                .tipoConta(contaEntity.getTipoConta())
                .dataCriacao(contaEntity.getDataCriacao())
                .build();
    }
}
