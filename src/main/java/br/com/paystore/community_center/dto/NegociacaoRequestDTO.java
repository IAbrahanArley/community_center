package br.com.paystore.community_center.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegociacaoRequestDTO {
    @NotBlank(message = "O ID do centro de origem é obrigatório")
    private String centroOrigemId;

    @NotBlank(message = "O ID do centro de destino é obrigatório")
    private String centroDestinoId;

    @NotNull(message = "Os recursos oferecidos são obrigatórios")
    @Valid
    private RecursosDTO recursosOferecidos;

    @NotNull(message = "Os recursos recebidos são obrigatórios")
    @Valid
    private RecursosDTO recursosRecebidos;
}