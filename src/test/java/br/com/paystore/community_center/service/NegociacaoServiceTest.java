package br.com.paystore.community_center.service;

import br.com.paystore.community_center.dto.NegociacaoRequestDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import br.com.paystore.community_center.dto.RecursosDTO;
import br.com.paystore.community_center.exceptions.BusinessException;
import br.com.paystore.community_center.model.CentroComunitario;
import br.com.paystore.community_center.model.Negociacao;
import br.com.paystore.community_center.model.Recursos;
import br.com.paystore.community_center.repositories.CentroComunitarioRepository;
import br.com.paystore.community_center.repositories.NegociacaoRepository;
import br.com.paystore.community_center.services.NegociacaoService;
import br.com.paystore.community_center.util.CentroMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NegociacaoServiceTest {

    private CentroComunitarioRepository centroRepository;
    private NegociacaoRepository negociacaoRepository;
    private CentroMapper mapper;
    private NegociacaoService service;

    @BeforeEach
    void setUp() {
        centroRepository = mock(CentroComunitarioRepository.class);
        negociacaoRepository = mock(NegociacaoRepository.class);
        mapper = mock(CentroMapper.class);
        service = new NegociacaoService(centroRepository, negociacaoRepository, mapper);
    }
    @Test
    void deveNegociarComSucessoQuandoCentrosValidosERegrasAtendidas() {

        String idOrigem = "centro1";
        String idDestino = "centro2";

        Recursos recursosOferecidos = Recursos.builder()
                .medicos(1)
                .voluntarios(1)
                .kitsMedicos(0)
                .veiculosTransporte(0)
                .cestasBasicas(0)
                .build();

        Recursos recursosRecebidos = Recursos.builder()
                .kitsMedicos(1)
                .medicos(0)
                .voluntarios(0)
                .veiculosTransporte(0)
                .cestasBasicas(0)
                .build();

        RecursosDTO recursosOferecidosDTO = RecursosDTO.builder()
                .medicos(1)
                .voluntarios(1)
                .kitsMedicos(0)
                .veiculosTransporte(0)
                .cestasBasicas(0)
                .build();

        RecursosDTO recursosRecebidosDTO = RecursosDTO.builder()
                .kitsMedicos(1)
                .medicos(0)
                .voluntarios(0)
                .veiculosTransporte(0)
                .cestasBasicas(0)
                .build();

        Recursos recursosOrigem = Recursos.builder()
                .medicos(5)
                .voluntarios(5)
                .kitsMedicos(5)
                .veiculosTransporte(5)
                .cestasBasicas(5)
                .build();

        Recursos recursosDestino = Recursos.builder()
                .medicos(5)
                .voluntarios(5)
                .kitsMedicos(5)
                .veiculosTransporte(5)
                .cestasBasicas(5)
                .build();

        CentroComunitario origem = CentroComunitario.builder()
                .id(idOrigem)
                .capacidadeMaxima(100)
                .ocupacaoAtual(91)
                .recursos(recursosOrigem)
                .build();

        CentroComunitario destino = CentroComunitario.builder()
                .id(idDestino)
                .capacidadeMaxima(100)
                .ocupacaoAtual(91)
                .recursos(recursosDestino)
                .build();

        NegociacaoRequestDTO dto = NegociacaoRequestDTO.builder()
                .centroOrigemId(idOrigem)
                .centroDestinoId(idDestino)
                .recursosOferecidos(recursosOferecidosDTO)
                .recursosRecebidos(recursosRecebidosDTO)
                .build();

        Negociacao negociacao = Negociacao.builder()
                .centroOrigemId(idOrigem)
                .centroDestinoId(idDestino)
                .recursosEnviados(recursosOferecidos)
                .recursosRecebidos(recursosRecebidos)
                .regraFlexibilizada(false)
                .dataHora(LocalDateTime.now())
                .build();

        NegociacaoResponseDTO responseDTO = NegociacaoResponseDTO.builder()
                .centroOrigemId(idOrigem)
                .centroDestinoId(idDestino)
                .build();

        when(centroRepository.findById(idOrigem)).thenReturn(Optional.of(origem));
        when(centroRepository.findById(idDestino)).thenReturn(Optional.of(destino));
        when(mapper.paraEntidade(recursosOferecidosDTO)).thenReturn(recursosOferecidos);
        when(mapper.paraEntidade(recursosRecebidosDTO)).thenReturn(recursosRecebidos);
        when(negociacaoRepository.save(any(Negociacao.class))).thenReturn(negociacao);
        when(mapper.paraRespostaNegociacao(any(Negociacao.class))).thenReturn(responseDTO);

        NegociacaoResponseDTO resultado = service.negociar(dto);

        assertNotNull(resultado);
        assertEquals(idOrigem, resultado.getCentroOrigemId());
        assertEquals(idDestino, resultado.getCentroDestinoId());
        verify(centroRepository, times(2)).save(any(CentroComunitario.class));
    }


    @Test
    void deveLancarExcecaoQuandoCentrosNaoEncontrados() {
        NegociacaoRequestDTO dto = NegociacaoRequestDTO.builder()
                .centroOrigemId("centro1")
                .centroDestinoId("centro2")
                .build();

        when(centroRepository.findById("centro1")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.negociar(dto));
    }

    @Test
    void deveLancarExcecaoQuandoPontosDiferentesSemAltaOcupacao() {
        String idOrigem = "centro1";
        String idDestino = "centro2";

        RecursosDTO oferecidosDTO = RecursosDTO.builder().medicos(1).build();
        RecursosDTO recebidosDTO = RecursosDTO.builder().kitsMedicos(1).build();

        Recursos oferecidos = Recursos.builder().medicos(1).build();
        Recursos recebidos = Recursos.builder().kitsMedicos(1).build();

        CentroComunitario origem = CentroComunitario.builder()
                .id(idOrigem).capacidadeMaxima(100).ocupacaoAtual(10)
                .recursos(oferecidos).build();

        CentroComunitario destino = CentroComunitario.builder()
                .id(idDestino).capacidadeMaxima(100).ocupacaoAtual(10)
                .recursos(recebidos).build();

        NegociacaoRequestDTO dto = NegociacaoRequestDTO.builder()
                .centroOrigemId(idOrigem)
                .centroDestinoId(idDestino)
                .recursosOferecidos(oferecidosDTO)
                .recursosRecebidos(recebidosDTO)
                .build();

        when(centroRepository.findById(idOrigem)).thenReturn(Optional.of(origem));
        when(centroRepository.findById(idDestino)).thenReturn(Optional.of(destino));
        when(mapper.paraEntidade(oferecidosDTO)).thenReturn(oferecidos);
        when(mapper.paraEntidade(recebidosDTO)).thenReturn(recebidos);

        assertThrows(BusinessException.class, () -> service.negociar(dto));
    }

    @Test
    void deveListarNegociacoesPorCentroEData() {
        String centroId = "centro1";
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);

        Negociacao negociacao = Negociacao.builder()
                .centroOrigemId(centroId)
                .dataHora(LocalDateTime.now())
                .build();

        NegociacaoResponseDTO responseDTO = NegociacaoResponseDTO.builder()
                .centroOrigemId(centroId)
                .build();

        when(negociacaoRepository.findByCentroOrigemIdOrCentroDestinoIdAndDataHoraBetween(
                eq(centroId), eq(centroId), eq(inicio), any(LocalDateTime.class)))
                .thenReturn(List.of(negociacao));

        when(mapper.paraRespostaNegociacao(negociacao)).thenReturn(responseDTO);

        List<NegociacaoResponseDTO> resultado = service.listarPorCentroEData(centroId, inicio);

        assertEquals(1, resultado.size());
        assertEquals(centroId, resultado.get(0).getCentroOrigemId());
    }
}
