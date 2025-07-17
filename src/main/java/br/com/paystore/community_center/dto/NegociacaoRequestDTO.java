package br.com.paystore.community_center.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegociacaoRequestDTO {
    private String centroOrigemId;
    private String centroDestinoId;
    private RecursosDTO recursosOferecidos;
    private RecursosDTO recursosRecebidos;
}