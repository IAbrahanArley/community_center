package br.com.paystore.community_center.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "centros_comunitarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroComunitario {

    @Id
    private String id;

    private String nome;
    private String endereco;
    private String localizacao;
    private Integer capacidadeMaxima;
    private Integer ocupacaoAtual;

    private Recursos recursos;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
