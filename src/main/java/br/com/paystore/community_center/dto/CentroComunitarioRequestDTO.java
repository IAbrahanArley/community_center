package br.com.paystore.community_center.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroComunitarioRequestDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "A localização é obrigatória")
    private String localizacao;

    @NotNull(message = "A capacidade máxima é obrigatória")
    @Positive(message = "A capacidade máxima deve ser maior que zero")
    private Integer capacidadeMaxima;

    @NotNull(message = "A ocupação atual é obrigatória")
    @Min(value = 0, message = "A ocupação atual não pode ser negativa")
    private Integer ocupacaoAtual;

    @NotNull(message = "Os recursos são obrigatórios")
    private RecursosDTO recursos;
}
