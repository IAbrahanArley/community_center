package br.com.paystore.community_center.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroComunitarioRequestDTO {
    private String nome;
    private String endereco;
    private String localizacao;
    private Integer capacidadeMaxima;
    private Integer ocupacaoAtual;
    private RecursosDTO recursos;
}
