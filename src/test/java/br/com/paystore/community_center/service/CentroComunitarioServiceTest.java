package br.com.paystore.community_center.service;

import br.com.paystore.community_center.dto.CentroComunitarioRequestDTO;
import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.dto.RecursosDTO;
import br.com.paystore.community_center.model.CentroComunitario;
import br.com.paystore.community_center.model.Recursos;
import br.com.paystore.community_center.repositories.CentroComunitarioRepository;
import br.com.paystore.community_center.services.CentroComunitarioService;
import br.com.paystore.community_center.util.CentroMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CentroComunitarioServiceTest {

    @Mock
    private CentroComunitarioRepository repository;

    @Mock
    private CentroMapper mapper;

    @InjectMocks
    private CentroComunitarioService service;

    private CentroComunitario centro;
    private CentroComunitarioRequestDTO requestDTO;
    private CentroComunitarioResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        Recursos recursos = Recursos.builder().medicos(2).voluntarios(3).build();

        centro = CentroComunitario.builder()
                .id("1")
                .nome("Centro A")
                .endereco("Rua 1")
                .localizacao("SP")
                .capacidadeMaxima(100)
                .ocupacaoAtual(10)
                .recursos(recursos)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        RecursosDTO recursosDTO = RecursosDTO.builder().medicos(2).voluntarios(3).build();

        requestDTO = CentroComunitarioRequestDTO.builder()
                .nome("Centro A")
                .endereco("Rua 1")
                .localizacao("SP")
                .capacidadeMaxima(100)
                .ocupacaoAtual(10)
                .recursos(recursosDTO)
                .build();

        responseDTO = CentroComunitarioResponseDTO.builder()
                .id("1")
                .nome("Centro A")
                .endereco("Rua 1")
                .localizacao("SP")
                .capacidadeMaxima(100)
                .ocupacaoAtual(10)
                .recursos(recursosDTO)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
    }

    @Test
    void deveCriarCentroComSucesso() {
        when(mapper.paraEntidade(requestDTO)).thenReturn(centro);
        when(repository.save(any(CentroComunitario.class))).thenReturn(centro);
        when(mapper.paraResposta(centro)).thenReturn(responseDTO);

        CentroComunitarioResponseDTO result = service.criarCentro(requestDTO);

        assertNotNull(result);
        assertEquals("Centro A", result.getNome());
        verify(repository).save(any(CentroComunitario.class));
    }

    @Test
    void deveListarTodosOsCentros() {
        when(repository.findAll()).thenReturn(List.of(centro));
        when(mapper.paraResposta(any())).thenReturn(responseDTO);

        List<CentroComunitarioResponseDTO> result = service.listarTodos();

        assertEquals(1, result.size());
        assertEquals("Centro A", result.get(0).getNome());
    }

    @Test
    void deveBuscarCentroPorId() {
        when(repository.findById("1")).thenReturn(Optional.of(centro));
        when(mapper.paraResposta(centro)).thenReturn(responseDTO);

        CentroComunitarioResponseDTO result = service.buscarPorId("1");

        assertEquals("Centro A", result.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarCentroInexistente() {
        when(repository.findById("2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarPorId("2"));
        assertEquals("Centro não encontrado", ex.getMessage());
    }

    @Test
    void deveAtualizarCentro() {
        when(repository.findById("1")).thenReturn(Optional.of(centro));
        when(mapper.paraEntidade(requestDTO.getRecursos())).thenReturn(centro.getRecursos());
        when(repository.save(any())).thenReturn(centro);
        when(mapper.paraResposta(centro)).thenReturn(responseDTO);

        CentroComunitarioResponseDTO result = service.atualizar("1", requestDTO);

        assertEquals("Centro A", result.getNome());
        verify(repository).save(any(CentroComunitario.class));
    }

    @Test
    void deveDeletarCentro() {
        when(repository.existsById("1")).thenReturn(true);

        service.deletar("1");

        verify(repository).deleteById("1");
    }

    @Test
    void deveLancarExcecaoQuandoDeletarCentroInexistente() {
        when(repository.existsById("2")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deletar("2"));
        assertEquals("Centro não encontrado", ex.getMessage());
    }

    @Test
    void deveAtualizarOcupacao() {
        when(repository.findById("1")).thenReturn(Optional.of(centro));
        when(repository.save(any())).thenReturn(centro);

        service.atualizarOcupacao("1", 50);

        assertEquals(50, centro.getOcupacaoAtual());
        verify(repository).save(any(CentroComunitario.class));
    }
}
