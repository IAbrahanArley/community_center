package br.com.paystore.community_center.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "negociacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Negociacao {

    @Id
    private String id;

    private String centroOrigemId;
    private String centroDestinoId;

    private Recursos recursosEnviados;
    private Recursos recursosRecebidos;

    private LocalDateTime dataHora;

    private Boolean regraFlexibilizada;
}
