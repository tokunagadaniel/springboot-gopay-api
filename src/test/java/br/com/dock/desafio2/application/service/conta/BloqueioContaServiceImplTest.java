package br.com.dock.desafio2.application.service.conta;

import br.com.dock.desafio2.application.datastore.ContaDatastore;
import br.com.dock.desafio2.application.exception.BusinessException;
import br.com.dock.desafio2.domain.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {BloqueiaContaServiceImpl.class})
public class BloqueioContaServiceImplTest {

    @Autowired
    private BloqueiaContaService service;

    @MockBean
    private ContaDatastore datastore;

    private Conta conta;

    @BeforeEach
    public void setup() {
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
    public void bloqueiaConta_inputEhValido_atualizaContaComSucesso() {
        when(datastore.get(any(), anyBoolean()))
                .thenReturn(Optional.of(conta));

        service.process(conta.getIdConta());

        verify(datastore, times(1)).get(any(), anyBoolean());
        verify(datastore, times(1)).put(any());
    }

    @Test
    public void bloqueiaConta_inputEhInvalido_atualizaComFalha() {
        when(datastore.get(any(), anyBoolean()))
                .thenThrow(BusinessException.class);

        Exception exception = assertThrows(BusinessException.class, () -> {
            service.process(conta.getIdConta());
        });

        assertTrue(exception instanceof BusinessException);

        verify(datastore, times(1)).get(any(), anyBoolean());
        verify(datastore, times(0)).put(any());
    }

    @Test
    public void bloqueiaConta_inputEhInvalidoComStatus_atualizaComFalha() {
        conta.setFlagAtivo(false);

        when(datastore.get(any(), anyBoolean()))
                .thenReturn(Optional.of(conta));

        Exception exception = assertThrows(BusinessException.class, () -> {
            service.process(conta.getIdConta());
        });

        assertTrue(exception instanceof BusinessException);

        verify(datastore, times(1)).get(any(), anyBoolean());
        verify(datastore, times(0)).put(any());
    }
}
