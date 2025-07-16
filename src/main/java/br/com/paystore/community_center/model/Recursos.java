package br.com.paystore.community_center.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recursos {
    private Integer medicos = 0;
    private Integer voluntarios = 0;
    private Integer kitsMedicos = 0;
    private Integer veiculosTransporte = 0;
    private Integer cestasBasicas = 0;
}
