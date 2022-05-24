package br.com.tokunaga.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Pessoa implements Serializable {
    private String idPessoa;
    private String nomePessoa;
    private String cpfPessoa;
    private LocalDate dataNascimento;
}
