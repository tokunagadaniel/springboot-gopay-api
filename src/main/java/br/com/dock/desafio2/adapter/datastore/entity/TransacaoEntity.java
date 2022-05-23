package br.com.dock.desafio2.adapter.datastore.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Table(value = "tb_transacao")
@Builder
@Getter
public class TransacaoEntity implements Serializable {
    //@PrimaryKey
    //private TransacaoEntityPK key;

    @PrimaryKeyColumn(value = "id_conta", type = PrimaryKeyType.PARTITIONED)
    private UUID idConta;

    @PrimaryKeyColumn(value = "data_transacao", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private LocalDate dataTransacao;

    @PrimaryKeyColumn(value = "id_transacao", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private Long idTransacao;

    @Column(value = "valor")
    private Double valor;
}
