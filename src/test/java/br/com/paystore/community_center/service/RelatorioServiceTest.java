package br.com.paystore.community_center.service;

import br.com.paystore.community_center.dto.MediaRecursosDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import br.com.paystore.community_center.exceptions.BusinessException;
import br.com.paystore.community_center.model.CentroComunitario;
import br.com.paystore.community_center.model.Negociacao;
import br.com.paystore.community_center.model.Recursos;
import br.com.paystore.community_center.repositories.CentroComunitarioRepository;
import br.com.paystore.community_center.repositories.NegociacaoRepository;
import br.com.paystore.community_center.services.RelatorioService;
import br.com.paystore.community_center.util.CentroMapper;
import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RelatorioServiceTest {

    private CentroComunitarioRepository centroRepository;
    private NegociacaoRepository negociacaoRepository;
    private CentroMapper mapper;
    private RelatorioService relatorioService;

    @BeforeEach
    void setUp() {
        centroRepository = mock(CentroComunitarioRepository.class);
        negociacaoRepository = mock(NegociacaoRepository.class);
        mapper = mock(CentroMapper.class);
        relatorioService = new RelatorioService(centroRepository, negociacaoRepository, mapper);
    }

    @Test
    void deveRetornarCentrosComAltaOcupacao() {
        CentroComunitario centro = CentroComunitario.builder()
                .id("1")
                .ocupacaoAtual(91)
                .capacidadeMaxima(100)
                .build();

        CentroComunitarioResponseDTO dto = CentroComunitarioResponseDTO.builder()
                .id("1")
                .ocupacaoAtual(91)
                .capacidadeMaxima(100)
                .build();

        when(centroRepository.findAll()).thenReturn(List.of(centro));
        when(mapper.paraResposta(centro)).thenReturn(dto);

        List<CentroComunitarioResponseDTO> resultado = relatorioService.listarCentrosComAltaOcupacao();

        assertEquals(1, resultado.size());
        assertEquals("1", resultado.get(0).getId());
    }

    @Test
    void deveCalcularMediaRecursosPorCentro() {
        Recursos recursos1 = Recursos.builder()
                .medicos(4)
                .voluntarios(6)
                .kitsMedicos(2)
                .veiculosTransporte(3)
                .cestasBasicas(10)
                .build();

        Recursos recursos2 = Recursos.builder()
                .medicos(2)
                .voluntarios(4)
                .kitsMedicos(0)
                .veiculosTransporte(1)
                .cestasBasicas(6)
                .build();

        CentroComunitario c1 = CentroComunitario.builder().recursos(recursos1).build();
        CentroComunitario c2 = CentroComunitario.builder().recursos(recursos2).build();

        when(centroRepository.findAll()).thenReturn(List.of(c1, c2));

        MediaRecursosDTO resultado = relatorioService.calcularMediaRecursosPorCentro();

        assertEquals(3.0, resultado.getMedicos());
        assertEquals(5.0, resultado.getVoluntarios());
        assertEquals(1.0, resultado.getKitsMedicos());
        assertEquals(2.0, resultado.getVeiculosTransporte());
        assertEquals(8.0, resultado.getCestasBasicas());
    }

    @Test
    void deveListarNegociacoesPorCentroEData() {
        String centroId = "centro1";
        LocalDateTime inicio = LocalDateTime.now().minusHours(3);
        LocalDateTime agora = LocalDateTime.now();

        Negociacao negociacao = Negociacao.builder()
                .centroOrigemId(centroId)
                .dataHora(agora.minusHours(1))
                .build();

        NegociacaoResponseDTO responseDTO = NegociacaoResponseDTO.builder()
                .centroOrigemId(centroId)
                .build();

        when(negociacaoRepository
                .findByCentroOrigemIdOrCentroDestinoIdAndDataHoraBetween(
                        eq(centroId), eq(centroId), eq(inicio), any(LocalDateTime.class)))
                .thenReturn(List.of(negociacao));

        when(mapper.paraRespostaNegociacao(negociacao)).thenReturn(responseDTO);

        List<NegociacaoResponseDTO> resultado = relatorioService.listarNegociacoesPorCentro(centroId, inicio, null);

        assertEquals(1, resultado.size());
        assertEquals(centroId, resultado.get(0).getCentroOrigemId());
    }

    @Test
    void deveLancarExcecaoQuandoCentroOuDataInvalido() {
        assertThrows(BusinessException.class, () ->
                relatorioService.listarNegociacoesPorCentro(null, LocalDateTime.now(), null));

        assertThrows(BusinessException.class, () ->
                relatorioService.listarNegociacoesPorCentro("centro1", null, null));
    }
}
