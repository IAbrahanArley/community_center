package br.com.paystore.community_center.controllers;

import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.dto.MediaRecursosDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Relatorios", description = "Endpoints para gerenciar relatorios")
public interface RelatorioApi {

    @Operation(summary = "Buscar centro", description = "Endpoint para buscar centros comunitarios com alta ocupação",
            tags ={"Relatorios"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content =  @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CentroComunitarioResponseDTO.class))
                    )
            ),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<List<CentroComunitarioResponseDTO>> centrosAltaOcupacao();

    @Operation(summary = "Buscar media de recursos", description = "Endpoint para buscar  media de recursos de centros comunitarios",
            tags ={"Relatorios"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = MediaRecursosDTO.class))
            ),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<MediaRecursosDTO> mediaRecursos();

    @Operation(summary = "Buscar historico de negociacoes", description = "Endpoint para buscar de negociacoes de centros comunitarios",
            tags ={"Relatorios"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content =  @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NegociacaoResponseDTO.class))
                    )
            ),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<List<NegociacaoResponseDTO>> historicoNegociacoes(
            @RequestParam String centroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim);
}
