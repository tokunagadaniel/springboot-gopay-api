package br.com.dock.desafio2.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Conta implements Serializable {
    private String idConta;
    private String idPessoa;
    private double saldo;
    private double limiteSaqueDiario;
    private boolean flagAtivo;
    private int tipoConta;
    private LocalDateTime dataCriacao;
}
