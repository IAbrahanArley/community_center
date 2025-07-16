package br.com.paystore.community_center.controllers;

import br.com.paystore.community_center.dto.CentroComunitarioRequestDTO;
import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Centros comunitarios", description = "Endpoints para gerenciar centros comunitarios")
public interface CentroComunitarioApi {

    @Operation(summary = "Criar centro comunitarios", description = "Endpoint para criar centros comunitarios",
            tags ={"Centros comunitarios"}, responses = {
            @ApiResponse(description = "Created", responseCode = "201",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CentroComunitarioResponseDTO.class))
                            )
                    }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<CentroComunitarioResponseDTO> criarCentro(
            @RequestBody CentroComunitarioRequestDTO dto);

    @Operation(summary = "Buscar centros comunitarios", description = "Endpoint para buscar todos centros comunitarios",
            tags ={"Centros comunitarios"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CentroComunitarioResponseDTO.class))
            ),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<List<CentroComunitarioResponseDTO>> listar();

    @Operation(summary = "Buscar por id", description = "Endpoints para buscar centro comunitario por id",
            tags ={"Centros comunitarios"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CentroComunitarioResponseDTO.class))
                            )
                    }),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<CentroComunitarioResponseDTO> buscarPorId(@PathVariable String id);

    @Operation(summary = "Modificar por id", description = "Endpoint para modificar o centro comunitario por ID",
            tags ={"Centros comunitarios"}, responses = {
            @ApiResponse(description = "Updated", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CentroComunitarioResponseDTO.class))
            ),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<CentroComunitarioResponseDTO> atualizar(
            @PathVariable String id,
            @RequestBody CentroComunitarioRequestDTO dto);

    @Operation(summary = "Deletar", description = "Endpoint para deletar um centro comunitario por id",
            tags = {"Centros comunitarios"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<Void> deletar(@PathVariable String id);

    @Operation(summary = "Atualizar Ocupação", description = "Endpoint para atualizar a ocupação de um centro comunitario por id",
            tags = {"Centros comunitarios"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<Void> atualizarOcupacao(
            @PathVariable String id,
            @RequestParam int ocupacao);
}
