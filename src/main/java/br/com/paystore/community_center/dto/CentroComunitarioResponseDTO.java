package br.com.paystore.community_center.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroComunitarioResponseDTO {
    private String id;
    private String nome;
    private String endereco;
    private String localizacao;
    private Integer capacidadeMaxima;
    private Integer ocupacaoAtual;
    private RecursosDTO recursos;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
