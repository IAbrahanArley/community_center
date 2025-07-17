package br.com.paystore.community_center.controllers;

import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.dto.NegociacaoRequestDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Negociação", description = "Endpoints para gerenciar negociações entre centros comunitarios")
public interface NegociacaoApi {

    @Operation(summary = "Criar negociação", description = "Endpoint para criar negociacoes entre centros comunitarios",
            tags ={"Negociação"}, responses = {
            @ApiResponse(description = "Created", responseCode = "201",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = NegociacaoRequestDTO.class))
                            )
                    }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    NegociacaoResponseDTO negociar(@RequestBody NegociacaoRequestDTO dto);

    @Operation(summary = "Buscar negociacoes", description = "Endpoint para buscar negociacoes entre centros comunitarios",
            tags ={"Negociação"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = NegociacaoResponseDTO.class))
            ),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    List<NegociacaoResponseDTO> listarPorCentroEData(
            @RequestParam String centroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde);
}
