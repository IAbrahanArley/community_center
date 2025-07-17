package br.com.paystore.community_center.services;


import br.com.paystore.community_center.dto.*;
import br.com.paystore.community_center.exceptions.BusinessException;
import br.com.paystore.community_center.model.*;
import br.com.paystore.community_center.repositories.*;
import br.com.paystore.community_center.util.CentroMapper;
import br.com.paystore.community_center.util.PontosRecurso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NegociacaoService {

    private final CentroComunitarioRepository centroRepository;
    private final NegociacaoRepository negociacaoRepository;
    private final CentroMapper mapper;

    public NegociacaoResponseDTO negociar(NegociacaoRequestDTO dto) {
        CentroComunitario origem = centroRepository.findById(dto.getCentroOrigemId())
                .orElseThrow(() -> new BusinessException("Centro origem não encontrado"));
        CentroComunitario destino = centroRepository.findById(dto.getCentroDestinoId())
                .orElseThrow(() -> new BusinessException("Centro destino não encontrado"));

        Recursos recursosOferecidos = mapper.paraEntidade(dto.getRecursosOferecidos());
        Recursos recursosRecebidos = mapper.paraEntidade(dto.getRecursosRecebidos());

        int pontosOrigem = PontosRecurso.calcularTotalPontos(recursosOferecidos);
        int pontosDestino = PontosRecurso.calcularTotalPontos(recursosRecebidos);

        boolean origemComAltaOcupacao = origem.getOcupacaoAtual() >= (origem.getCapacidadeMaxima() * 0.9);
        boolean destinoComAltaOcupacao = destino.getOcupacaoAtual() >= (destino.getCapacidadeMaxima() * 0.9);

        if (!origemComAltaOcupacao && !destinoComAltaOcupacao && pontosOrigem != pontosDestino) {
            throw new BusinessException("Negociação inválida: pontos diferentes e nenhum centro com alta ocupação.");
        }

        origem.setRecursos(subtrairRecursos(origem.getRecursos(), recursosOferecidos));
        origem.setRecursos(somarRecursos(origem.getRecursos(), recursosRecebidos));
        destino.setRecursos(subtrairRecursos(destino.getRecursos(), recursosRecebidos));
        destino.setRecursos(somarRecursos(destino.getRecursos(), recursosOferecidos));

        centroRepository.save(origem);
        centroRepository.save(destino);

        Negociacao negociacao = Negociacao.builder()
                .centroOrigemId(origem.getId())
                .centroDestinoId(destino.getId())
                .recursosEnviados(recursosOferecidos)
                .recursosRecebidos(recursosRecebidos)
                .regraFlexibilizada(origemComAltaOcupacao || destinoComAltaOcupacao)
                .data(LocalDateTime.now())
                .build();

        return mapper.paraRespostaNegociacao(negociacaoRepository.save(negociacao));
    }

    public List<NegociacaoResponseDTO> listarPorCentroEData(String centroId, LocalDateTime inicio) {
        List<Negociacao> negociacoes = negociacaoRepository
                .findByCentroOrigemIdOrCentroDestinoIdEDataHora(
                        centroId, centroId, inicio, LocalDateTime.now()
                );

        return negociacoes.stream()
                .map(mapper::paraRespostaNegociacao)
                .toList();
    }

    private Recursos somarRecursos(Recursos a, Recursos b) {
        return Recursos.builder()
                .medicos(a.getMedicos() + b.getMedicos())
                .voluntarios(a.getVoluntarios() + b.getVoluntarios())
                .kitsMedicos(a.getKitsMedicos() + b.getKitsMedicos())
                .veiculosTransporte(a.getVeiculosTransporte() + b.getVeiculosTransporte())
                .cestasBasicas(a.getCestasBasicas() + b.getCestasBasicas())
                .build();
    }

    private Recursos subtrairRecursos(Recursos a, Recursos b) {
        return Recursos.builder()
                .medicos(a.getMedicos() - b.getMedicos())
                .voluntarios(a.getVoluntarios() - b.getVoluntarios())
                .kitsMedicos(a.getKitsMedicos() - b.getKitsMedicos())
                .veiculosTransporte(a.getVeiculosTransporte() - b.getVeiculosTransporte())
                .cestasBasicas(a.getCestasBasicas() - b.getCestasBasicas())
                .build();
    }
}