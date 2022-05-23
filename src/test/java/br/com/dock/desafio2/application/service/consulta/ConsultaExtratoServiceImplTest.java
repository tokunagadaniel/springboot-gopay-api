package br.com.dock.desafio2.application.service.consulta;

import br.com.dock.desafio2.application.datastore.TransacaoDatastore;
import br.com.dock.desafio2.domain.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {ConsultaExtratoServiceImpl.class})
public class ConsultaExtratoServiceImplTest {

    @Autowired
    private ConsultaExtratoService service;

    @MockBean
    private TransacaoDatastore datastore;

    private Transacao transacao;

    @BeforeEach
    public void setup() {
        transacao = Transacao.builder()
                .idConta(randomBasedGenerator().generate().toString())
                .valor(100f)
                .idTransacao(1L)
                .dataTransacao(LocalDate.now())
                .build();
    }

    @Test
    public void consultaExtratoComDuasDatas_inputEhValido_consultaComSucesso() {
        when(datastore.get(any(), any(), any()))
                .thenReturn(List.of(transacao));

        service.process(transacao.getIdConta(), "21/03/2022", "21/03/2022");

        verify(datastore, times(1)).get(any(), any(), any());
    }

    @Test
    public void consultaExtratoSemDatas_inputEhValido_consultaComSucesso() {
        when(datastore.get(any(), any(), any()))
                .thenReturn(List.of(transacao));

        service.process(transacao.getIdConta(), "", "");

        verify(datastore, times(1)).get(any());
    }
}
