package br.com.tokunaga.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Transacao {
    private Long idTransacao;
    private String idConta;
    private double valor;
    private LocalDate dataTransacao;
}
