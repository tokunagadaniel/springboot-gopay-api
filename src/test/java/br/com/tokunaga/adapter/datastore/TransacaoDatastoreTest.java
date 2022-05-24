package br.com.tokunaga.adapter.datastore;

import br.com.tokunaga.adapter.datastore.entity.TransacaoEntity;
import br.com.tokunaga.adapter.datastore.mapper.DatastoreMapper;
import br.com.tokunaga.adapter.datastore.repository.TransacaoRepository;
import br.com.tokunaga.application.datastore.TransacaoDatastore;
import br.com.tokunaga.application.exception.DatastoreException;
import br.com.tokunaga.domain.Transacao;
import com.datastax.oss.driver.api.core.servererrors.InvalidQueryException;
import com.datastax.oss.driver.api.core.servererrors.UnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {TransacaoDatastoreImpl.class})
public class TransacaoDatastoreTest {

    @Autowired
    private TransacaoDatastore datastore;

    @MockBean
    private DatastoreMapper<Transacao, TransacaoEntity> mapper;

    @MockBean
    private TransacaoRepository repository;

    private Transacao transacao;
    private TransacaoEntity entity;

    @BeforeEach
    public void setup() {
        transacao = Transacao.builder()
                .idConta(randomBasedGenerator().generate().toString())
                .valor(100f)
                .idTransacao(1L)
                .dataTransacao(LocalDate.now())
                .build();

        entity = TransacaoEntity.builder()
                .idConta(UUID.fromString(transacao.getIdConta()))
                .valor(transacao.getValor())
                .idTransacao(transacao.getIdTransacao())
                .dataTransacao(transacao.getDataTransacao())
                .build();
    }

    /* Testes de Put */
    @Test
    public void insereNoBanco_inputEhValido_insercaoComSucesso() {
        when(mapper.mapToDatabase(any()))
                .thenReturn(entity);

        datastore.put(transacao);

        verify(mapper, times(1)).mapToDatabase(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void insereNoBanco_inputNaoEhValidoNoMapper_insercaoComFalha() {
        when(mapper.mapToDatabase(any()))
                .thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.put(transacao);
        });

        assertTrue(exception instanceof DatastoreException);
    }

    @Test
    public void insereNoBanco_inputNaoEhValidoNaChave_insercaoComFalha() {
        transacao.setIdConta("");

        when(repository.save(any()))
                .thenThrow(InvalidQueryException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.put(transacao);
        });

        assertTrue(exception instanceof DatastoreException);
    }


    /* Testes de Get com 1 Argumento */
    @Test
    public void recuperaNoBancoUmArgumento_inputEhValido_recuperacaoComSucesso() {

        when(repository.findByIdConta(any()))
                .thenReturn((Collection) List.of(entity));

        when(mapper.mapFromDatabase(any()))
                .thenReturn(transacao);

        datastore.get(transacao.getIdConta());

        verify(mapper, times(1)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBancoUmArgumento_inputEhInvalidoComUuid_recuperacaoComFalha() {

        when(repository.findByIdConta(any()))
                .thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(transacao.getIdConta());
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBancoUmArgumento_inputEhValidoBancoIndisponivel_recuperacaoComFalha() {
        when(repository.findByIdConta(any()))
                .thenThrow(UnavailableException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(transacao.getIdConta());
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }


    /* Testes de Get com 2 Argumento */
    @Test
    public void recuperaNoBancoDoisArgumento_inputEhValido_recuperacaoComSucesso() {

        when(repository.findByIdContaAndDataTransacao(any(), any()))
                .thenReturn((Collection) List.of(entity));

        when(mapper.mapFromDatabase(any()))
                .thenReturn(transacao);

        datastore.get(transacao.getIdConta(), transacao.getDataTransacao());

        verify(mapper, times(1)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBancoDoisArgumento_inputEhInvalidoComUuid_recuperacaoComFalha() {

        when(repository.findByIdContaAndDataTransacao(any(), any()))
                .thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(transacao.getIdConta(), transacao.getDataTransacao());
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBancoDoisArgumento_inputEhValidoBancoIndisponivel_recuperacaoComFalha() {
        when(repository.findByIdContaAndDataTransacao(any(), any()))
                .thenThrow(UnavailableException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(transacao.getIdConta(), transacao.getDataTransacao());
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }


    /* Testes de Get com 3 Argumento */
    @Test
    public void recuperaNoBancoTresArgumento_inputEhValido_recuperacaoComSucesso() {

        when(repository.findByIdContaAndDataTransacaoBetween(any(), any(), any()))
                .thenReturn((Collection) List.of(entity));

        when(mapper.mapFromDatabase(any()))
                .thenReturn(transacao);

        datastore.get(transacao.getIdConta(), "01/01/2015", "25/03/2022");

        verify(mapper, times(1)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBancoTresArgumento_inputEhInvalidoComUuid_recuperacaoComFalha() {

        when(repository.findByIdContaAndDataTransacaoBetween(any(), any(), any()))
                .thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(transacao.getIdConta(), "01/01/2015", "25/03/2022");
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBancoTresArgumento_inputEhValidoBancoIndisponivel_recuperacaoComFalha() {
        when(repository.findByIdContaAndDataTransacaoBetween(any(), any(), any()))
                .thenThrow(UnavailableException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(transacao.getIdConta(), "01/01/2015", "25/03/2022");
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBancoTresArgumento_inputEhInvalido_recuperacaoComFalha() {
        when(repository.findByIdContaAndDataTransacao(any(), any()))
                .thenThrow(DateTimeParseException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(transacao.getIdConta(), "01/01/2015", "--/--/----");
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }
}
