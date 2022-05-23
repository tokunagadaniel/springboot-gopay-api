package br.com.dock.desafio2.adapter.datastore.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "tb_pessoa")
@Builder
@Getter
public class PessoaEntity implements Serializable {

    @PrimaryKey(value = "id_pessoa")
    private UUID idPessoa;

    @Column(value = "nome")
    private String nomePessoa;

    @Column(value = "cpf")
    private String cpfPessoa;

    @Column(value = "data_nascimento")
    private LocalDate dataNascimento;
}
