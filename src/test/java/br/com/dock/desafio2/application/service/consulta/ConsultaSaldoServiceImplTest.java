package br.com.dock.desafio2.application.service.consulta;

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
@ContextConfiguration(classes = {ConsultaSaldoServiceImpl.class})
public class ConsultaSaldoServiceImplTest {

    @Autowired
    private ConsultaSaldoService service;

    @MockBean
    private ContaDatastore datastore;

    private Conta conta;

    @BeforeEach
    public void setup() {
        conta = Conta.builder()
                .idConta(randomBasedGenerator().generate().toString())
                .limiteSaqueDiario(100f)
                .dataCriacao(LocalDateTime.now())
                .saldo(150)
                .flagAtivo(true)
                .tipoConta(1)
                .idPessoa(randomBasedGenerator().generate().toString())
                .build();
    }

    @Test
    public void consultaSaldo_inputEhValido_consultaComSucesso() {
        when(datastore.get(any(), anyBoolean()))
                .thenReturn(Optional.of(conta));

        service.process(conta.getIdConta());

        verify(datastore, times(1)).get(any(), anyBoolean());
    }

    @Test
    public void consultaSaldo_inputEhInvalido_consultaComFalha() {
        when(datastore.get(any(), anyBoolean()))
                .thenThrow(BusinessException.class);

        Exception exception = assertThrows(BusinessException.class, () -> {
            service.process(conta.getIdConta());
        });

        assertTrue(exception instanceof BusinessException);

        verify(datastore, times(1)).get(any(), anyBoolean());
    }
}
