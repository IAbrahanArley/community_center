package br.com.paystore.community_center.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecursosDTO {
    private Integer medicos;
    private Integer voluntarios;
    private Integer kitsMedicos;
    private Integer veiculosTransporte;
    private Integer cestasBasicas;
}

