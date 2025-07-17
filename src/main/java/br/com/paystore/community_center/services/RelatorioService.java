package br.com.paystore.community_center.services;

import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.dto.MediaRecursosDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import br.com.paystore.community_center.exceptions.BusinessException;
import br.com.paystore.community_center.model.CentroComunitario;
import br.com.paystore.community_center.model.Recursos;
import br.com.paystore.community_center.repositories.CentroComunitarioRepository;
import br.com.paystore.community_center.repositories.NegociacaoRepository;
import br.com.paystore.community_center.util.CentroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelatorioService {
    private final CentroComunitarioRepository centroRepository;
    private final NegociacaoRepository negociacaoRepository;
    private final CentroMapper mapper;

    public List<CentroComunitarioResponseDTO> listarCentrosComAltaOcupacao() {
        List<CentroComunitario> centros = centroRepository.findAll();

        return centros.stream()
                .filter(c -> {
                    double ocupacao = (double) c.getOcupacaoAtual() / c.getCapacidadeMaxima();
                    return ocupacao >= 0.9;
                })
                .map(mapper::paraResposta)
                .toList();
    }

    public MediaRecursosDTO calcularMediaRecursosPorCentro() {
        List<CentroComunitario> centros = centroRepository.findAll();
        int total = centros.size();

        if (total == 0) return new MediaRecursosDTO(0, 0, 0, 0, 0);

        int somaMed = 0, somaVol = 0, somaKit = 0, somaVei = 0, somaCes = 0;

        for (CentroComunitario c : centros) {
            Recursos r = c.getRecursos();
            somaMed += Optional.ofNullable(r.getMedicos()).orElse(0);
            somaVol += Optional.ofNullable(r.getVoluntarios()).orElse(0);
            somaKit += Optional.ofNullable(r.getKitsMedicos()).orElse(0);
            somaVei += Optional.ofNullable(r.getVeiculosTransporte()).orElse(0);
            somaCes += Optional.ofNullable(r.getCestasBasicas()).orElse(0);
        }

        return MediaRecursosDTO.builder()
                .medicos((double) somaMed / total)
                .voluntarios((double) somaVol / total)
                .kitsMedicos((double) somaKit / total)
                .veiculosTransporte((double) somaVei / total)
                .cestasBasicas((double) somaCes / total)
                .build();
    }

    public List<NegociacaoResponseDTO> listarNegociacoesPorCentro(String centroId, LocalDateTime inicio, LocalDateTime fim) {
        if (centroId == null || centroId.isBlank() || inicio == null) {
            throw new BusinessException("Centro e data de início são obrigatórios.");
        }

        LocalDateTime dataFim = fim != null ? fim : LocalDateTime.now();

        return negociacaoRepository
                .findByCentroOrigemIdOrCentroDestinoIdAndDataHoraBetween(
                        centroId, centroId, inicio, dataFim)
                .stream()
                .filter(n -> n.getCentroOrigemId().equals(centroId) || n.getCentroDestinoId().equals(centroId))
                .map(mapper::paraRespostaNegociacao)
                .toList();
    }
}
