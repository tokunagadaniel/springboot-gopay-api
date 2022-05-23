package br.com.dock.desafio2.adapter.datastore.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "tb_conta")
@Builder
@Getter
public class ContaEntity implements Serializable {

    @PrimaryKey(value = "id_conta")
    private UUID idConta;

    @Column(value = "id_pessoa")
    private UUID idPessoa;

    @Column(value = "saldo")
    private double saldo;

    @Column(value = "limite_saque_diario")
    private Double limiteSaqueDiario;

    @Column(value = "conta_ativa")
    private boolean flagAtivo;

    @Column(value = "tipo_conta")
    private int tipoConta;

    @Column(value = "data_criacao")
    private LocalDateTime dataCriacao;
}
