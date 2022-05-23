package br.com.dock.desafio2.adapter.datastore;

import br.com.dock.desafio2.adapter.datastore.entity.ContaEntity;
import br.com.dock.desafio2.adapter.datastore.mapper.DatastoreMapper;
import br.com.dock.desafio2.adapter.datastore.repository.ContaRepository;
import br.com.dock.desafio2.application.datastore.ContaDatastore;
import br.com.dock.desafio2.application.exception.BusinessException;
import br.com.dock.desafio2.application.exception.DatastoreException;
import br.com.dock.desafio2.domain.Conta;
import com.datastax.oss.driver.api.core.servererrors.InvalidQueryException;
import com.datastax.oss.driver.api.core.servererrors.UnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {ContaDatastoreImpl.class})
public class ContaDatastoreTest {

    @Autowired
    private ContaDatastore datastore;

    @MockBean
    private DatastoreMapper<Conta, ContaEntity> mapper;

    @MockBean
    private ContaRepository repository;

    private Conta conta;
    private ContaEntity entity;

    @BeforeEach
    public void setup() {
        conta = Conta.builder()
                .idConta(randomBasedGenerator().generate().toString())
                .idPessoa(randomBasedGenerator().generate().toString())
                .limiteSaqueDiario(0)
                .tipoConta(1)
                .saldo(0)
                .flagAtivo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        entity = ContaEntity.builder()
                .idConta(UUID.fromString(conta.getIdConta()))
                .idPessoa(UUID.fromString(conta.getIdPessoa()))
                .limiteSaqueDiario(conta.getLimiteSaqueDiario())
                .tipoConta(conta.getTipoConta())
                .saldo(conta.getSaldo())
                .flagAtivo(conta.isFlagAtivo())
                .dataCriacao(conta.getDataCriacao())
                .build();
    }

    /* Testes de Put */
    @Test
    public void insereNoBanco_inputEhValido_insercaoComSucesso() {
        when(mapper.mapToDatabase(any()))
                .thenReturn(entity);

        datastore.put(conta);

        verify(mapper, times(1)).mapToDatabase(any());
    }

    @Test
    public void insereNoBanco_inputNaoEhValidoNoMapper_insercaoComFalha() {
        when(mapper.mapToDatabase(any()))
                .thenThrow(ArrayIndexOutOfBoundsException.class);

        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            datastore.put(conta);
        });

        assertTrue(exception instanceof ArrayIndexOutOfBoundsException);
    }

    @Test
    public void insereNoBanco_inputNaoEhValidoNaChave_insercaoComFalha() {
        conta.setIdConta("");

        when(repository.save(any()))
                .thenThrow(InvalidQueryException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.put(conta);
        });

        assertTrue(exception instanceof DatastoreException);
    }


    /* Testes de Get */
    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoFalse_recuperacaoComSucesso() {
        when(repository.findById(any()))
                .thenReturn(Optional.of(entity));

        when(mapper.mapFromDatabase(any()))
                .thenReturn(conta);

        datastore.get(conta.getIdConta(), false);

        verify(mapper, times(1)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoTrue_recuperacaoComSucesso() {
        when(repository.findById(any()))
                .thenReturn(Optional.of(entity));

        when(mapper.mapFromDatabase(any()))
                .thenReturn(conta);

        datastore.get(conta.getIdConta(), true);

        verify(mapper, times(1)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoTrueBancoIndisponivel_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenThrow(UnavailableException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(conta.getIdConta(), true);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoFalseBancoIndisponivel_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenThrow(UnavailableException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(conta.getIdConta(), false);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhInvalidoComUuidELancaExcecaoFalse_recuperacaoComFalha() {
        conta.setIdConta("");

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(conta.getIdConta(), false);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhInvalidoComUuidELancaExcecaoTrue_recuperacaoComFalha() {
        conta.setIdConta("");

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(conta.getIdConta(), true);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoNaoRetornaLancaExcecaoFalse_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        datastore.get(conta.getIdConta(), false);

        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoNaoRetornaLancaExcecaoTrue_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(BusinessException.class, () -> {
            datastore.get(conta.getIdConta(), true);
        });

        assertTrue(exception instanceof BusinessException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }
}
