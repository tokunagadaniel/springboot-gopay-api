package br.com.dock.desafio2.adapter.datastore;

import br.com.dock.desafio2.adapter.datastore.entity.PessoaEntity;
import br.com.dock.desafio2.adapter.datastore.mapper.DatastoreMapper;
import br.com.dock.desafio2.adapter.datastore.repository.PessoaRepository;
import br.com.dock.desafio2.application.datastore.PessoaDatastore;
import br.com.dock.desafio2.application.exception.BusinessException;
import br.com.dock.desafio2.application.exception.DatastoreException;
import br.com.dock.desafio2.domain.Pessoa;
import com.datastax.oss.driver.api.core.servererrors.InvalidQueryException;
import com.datastax.oss.driver.api.core.servererrors.UnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.fasterxml.uuid.Generators.nameBasedGenerator;
import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {PessoaDatastoreImpl.class})
public class PessoaDatastoreTest {

    @Autowired
    private PessoaDatastore datastore;

    @MockBean
    private DatastoreMapper<Pessoa, PessoaEntity> mapper;

    @MockBean
    private PessoaRepository repository;

    private Pessoa pessoa;
    private PessoaEntity entity;

    @BeforeEach
    public void setup() {
        pessoa = Pessoa.builder()
                .idPessoa(nameBasedGenerator().generate("12345678912").toString())
                .nomePessoa("Batima Bernardes")
                .dataNascimento(LocalDate.now())
                .cpfPessoa("12345678912")
                .build();

        entity = PessoaEntity.builder()
                .idPessoa(UUID.fromString(pessoa.getIdPessoa()))
                .nomePessoa("Batima Bernardes")
                .dataNascimento(LocalDate.now())
                .cpfPessoa("12345678912")
                .build();
    }

    /* Testes de Put */
    @Test
    public void insereNoBanco_inputEhValido_insercaoComSucesso() {
        when(mapper.mapToDatabase(any()))
                .thenReturn(entity);

        datastore.put(pessoa);

        verify(mapper, times(1)).mapToDatabase(any());
    }

    @Test
    public void insereNoBanco_inputNaoEhValidoNoMapper_insercaoComFalha() {
        when(mapper.mapToDatabase(any()))
                .thenThrow(ArrayIndexOutOfBoundsException.class);

        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            datastore.put(pessoa);
        });

        assertTrue(exception instanceof ArrayIndexOutOfBoundsException);
    }

    @Test
    public void insereNoBanco_inputNaoEhValidoNaChave_insercaoComFalha() {
        pessoa.setIdPessoa("");

        when(repository.save(any()))
                .thenThrow(InvalidQueryException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.put(pessoa);
        });

        assertTrue(exception instanceof DatastoreException);
    }


    /* Testes de Get */
    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoFalse_recuperacaoComSucesso() {
        when(repository.findById(any()))
                .thenReturn(Optional.of(entity));

        when(mapper.mapFromDatabase(any()))
                .thenReturn(pessoa);

        datastore.get(pessoa.getIdPessoa(), false);

        verify(mapper, times(1)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoTrue_recuperacaoComSucesso() {
        when(repository.findById(any()))
                .thenReturn(Optional.of(entity));

        when(mapper.mapFromDatabase(any()))
                .thenReturn(pessoa);

        datastore.get(pessoa.getIdPessoa(), true);

        verify(mapper, times(1)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoTrueBancoIndisponivel_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenThrow(UnavailableException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(pessoa.getIdPessoa(), true);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoELancaExcecaoFalseBancoIndisponivel_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenThrow(UnavailableException.class);

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(pessoa.getIdPessoa(), false);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhInvalidoComUuidELancaExcecaoFalse_recuperacaoComFalha() {
        pessoa.setIdPessoa("");

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(pessoa.getIdPessoa(), false);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhInvalidoComUuidELancaExcecaoTrue_recuperacaoComFalha() {
        pessoa.setIdPessoa("");

        Exception exception = assertThrows(DatastoreException.class, () -> {
            datastore.get(pessoa.getIdPessoa(), true);
        });

        assertTrue(exception instanceof DatastoreException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoNaoRetornaLancaExcecaoFalse_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        datastore.get(pessoa.getIdPessoa(), false);

        verify(mapper, times(0)).mapFromDatabase(any());
    }

    @Test
    public void recuperaNoBanco_inputEhValidoNaoRetornaLancaExcecaoTrue_recuperacaoComFalha() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(BusinessException.class, () -> {
            datastore.get(pessoa.getIdPessoa(), true);
        });

        assertTrue(exception instanceof BusinessException);
        verify(mapper, times(0)).mapFromDatabase(any());
    }
}
