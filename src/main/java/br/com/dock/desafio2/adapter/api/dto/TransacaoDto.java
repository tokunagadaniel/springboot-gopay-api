package br.com.dock.desafio2.adapter.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TransacaoDto {
    private String idConta;
    private String idTransacao;
    private LocalDate dataTransacao;

    @Builder.Default
    private double valor = 0;
}
