package br.com.dock.desafio2.application.service.transacao;

import br.com.dock.desafio2.application.datastore.ContaDatastore;
import br.com.dock.desafio2.application.datastore.TransacaoDatastore;
import br.com.dock.desafio2.domain.Conta;
import br.com.dock.desafio2.domain.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {RealizaDepositoServiceImpl.class})
public class RealizaDepositoServiceImplTest {

    @Autowired
    private RealizaDepositoService service;

    @MockBean
    private ContaDatastore contaDatastore;

    @MockBean
    private TransacaoDatastore transacaoDatastore;

    private Conta conta;
    private Transacao transacao;

    @BeforeEach
    public void setup() {
        transacao = Transacao.builder()
                .idConta(randomBasedGenerator().generate().toString())
                .valor(100f)
                .idTransacao(1L)
                .dataTransacao(LocalDate.now())
                .build();

        conta = Conta.builder()
                .idConta(randomBasedGenerator().generate().toString())
                .idPessoa(randomBasedGenerator().generate().toString())
                .tipoConta(1)
                .flagAtivo(true)
                .saldo(0)
                .dataCriacao(LocalDateTime.now())
                .limiteSaqueDiario(150)
                .build();
    }

    @Test
    public void consultaExtratoComDuasDatas_inputEhValido_consultaComSucesso() {
        when(contaDatastore.get(any(), anyBoolean()))
                .thenReturn(Optional.of(conta));

        when(transacaoDatastore.get(any(), any()))
                .thenReturn(new ArrayList<>());

        service.process(transacao);

        verify(contaDatastore, times(1)).get(any(), anyBoolean());
        verify(transacaoDatastore, times(1)).get(any(), any());
        verify(contaDatastore, times(1)).put(any());
        verify(transacaoDatastore, times(1)).put(any());
    }
}
