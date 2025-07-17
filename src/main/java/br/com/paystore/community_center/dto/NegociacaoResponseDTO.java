package br.com.paystore.community_center.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegociacaoResponseDTO {
    private String id;
    private String centroOrigemId;
    private String centroDestinoId;
    private RecursosDTO recursosOferecidos;
    private RecursosDTO recursosRecebidos;
    private boolean regraFlexibilizada;
    private LocalDateTime dataHora;
}