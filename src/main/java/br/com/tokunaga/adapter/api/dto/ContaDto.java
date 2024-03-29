package br.com.tokunaga.adapter.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContaDto {
    private String idConta;
    private String idPessoa;
    private float limiteSaqueDiario;
    private int tipoConta;
}
