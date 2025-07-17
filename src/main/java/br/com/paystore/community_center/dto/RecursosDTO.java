package br.com.paystore.community_center.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecursosDTO {
    @NotNull
    @Min(value = 0, message = "Quantidade de médicos não pode ser negativa")
    private Integer medicos;

    @NotNull
    @Min(value = 0, message = "Quantidade de voluntários não pode ser negativa")
    private Integer voluntarios;

    @NotNull
    @Min(value = 0, message = "Quantidade de kits médicos não pode ser negativa")
    private Integer kitsMedicos;

    @NotNull
    @Min(value = 0, message = "Quantidade de veículos de transporte não pode ser negativa")
    private Integer veiculosTransporte;

    @NotNull
    @Min(value = 0, message = "Quantidade de cestas básicas não pode ser negativa")
    private Integer cestasBasicas;
}

