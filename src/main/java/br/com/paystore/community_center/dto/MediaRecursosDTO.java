package br.com.paystore.community_center.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaRecursosDTO {
    private double medicos;
    private double voluntarios;
    private double kitsMedicos;
    private double veiculosTransporte;
    private double cestasBasicas;
}
