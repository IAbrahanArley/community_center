package br.com.paystore.community_center.util;

import br.com.paystore.community_center.dto.CentroComunitarioRequestDTO;
import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import br.com.paystore.community_center.dto.RecursosDTO;
import br.com.paystore.community_center.model.CentroComunitario;
import br.com.paystore.community_center.model.Negociacao;
import br.com.paystore.community_center.model.Recursos;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CentroMapper {

    public CentroComunitario paraEntidade(CentroComunitarioRequestDTO dto) {
        return CentroComunitario.builder()
                .nome(dto.getNome())
                .endereco(dto.getEndereco())
                .localizacao(dto.getLocalizacao())
                .capacidadeMaxima(dto.getCapacidadeMaxima())
                .ocupacaoAtual(dto.getOcupacaoAtual() != null ? dto.getOcupacaoAtual() : 0)
                .recursos(paraEntidade(dto.getRecursos()))
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
    }

    public CentroComunitarioResponseDTO paraResposta(CentroComunitario centro) {
        return CentroComunitarioResponseDTO.builder()
                .id(centro.getId())
                .nome(centro.getNome())
                .endereco(centro.getEndereco())
                .localizacao(centro.getLocalizacao())
                .capacidadeMaxima(centro.getCapacidadeMaxima())
                .ocupacaoAtual(centro.getOcupacaoAtual())
                .recursos(paraDTO(centro.getRecursos()))
                .criadoEm(centro.getCriadoEm())
                .atualizadoEm(centro.getAtualizadoEm())
                .build();
    }

    public Recursos paraEntidade(RecursosDTO dto) {
        if (dto == null) return new Recursos();
        return Recursos.builder()
                .medicos(dto.getMedicos())
                .voluntarios(dto.getVoluntarios())
                .kitsMedicos(dto.getKitsMedicos())
                .veiculosTransporte(dto.getVeiculosTransporte())
                .cestasBasicas(dto.getCestasBasicas())
                .build();
    }

    private RecursosDTO paraDTO(Recursos recursos) {
        if (recursos == null) return new RecursosDTO();
        return RecursosDTO.builder()
                .medicos(recursos.getMedicos())
                .voluntarios(recursos.getVoluntarios())
                .kitsMedicos(recursos.getKitsMedicos())
                .veiculosTransporte(recursos.getVeiculosTransporte())
                .cestasBasicas(recursos.getCestasBasicas())
                .build();
    }

    public NegociacaoResponseDTO paraRespostaNegociacao(Negociacao negociacao) {
        return NegociacaoResponseDTO.builder()
                .id(negociacao.getId())
                .centroOrigemId(negociacao.getCentroOrigemId())
                .centroDestinoId(negociacao.getCentroDestinoId())
                .recursosOferecidos(paraDTO(negociacao.getRecursosEnviados()))
                .recursosRecebidos(paraDTO(negociacao.getRecursosRecebidos()))
                .regraFlexibilizada(negociacao.getRegraFlexibilizada())
                .dataHora(negociacao.getDataHora())
                .build();
    }

}
