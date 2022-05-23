package br.com.dock.desafio2.adapter.datastore.mapper;

import br.com.dock.desafio2.adapter.datastore.entity.ContaEntity;
import br.com.dock.desafio2.adapter.datastore.entity.PessoaEntity;
import br.com.dock.desafio2.adapter.datastore.utils.ConverteDataUtil;
import br.com.dock.desafio2.domain.Conta;
import br.com.dock.desafio2.domain.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.fasterxml.uuid.Generators.nameBasedGenerator;

@Component
@RequiredArgsConstructor
public class PessoaDatastoreMapper extends ConverteDataUtil implements DatastoreMapper<Pessoa, PessoaEntity> {

    @Override
    public PessoaEntity mapToDatabase(Pessoa pessoa) {
        String cpf = pessoa.getCpfPessoa();

        return PessoaEntity.builder()
                .idPessoa(nameBasedGenerator().generate(cpf))
                .nomePessoa(pessoa.getNomePessoa())
                .cpfPessoa(cpf)
                .dataNascimento(pessoa.getDataNascimento())
                .build();
    }

    @Override
    public Pessoa mapFromDatabase(PessoaEntity pessoaEntity) {
        return Pessoa.builder()
                .idPessoa(pessoaEntity.getIdPessoa().toString())
                .nomePessoa(pessoaEntity.getNomePessoa())
                .cpfPessoa(pessoaEntity.getCpfPessoa())
                .dataNascimento(pessoaEntity.getDataNascimento())
                .build();
    }
}
