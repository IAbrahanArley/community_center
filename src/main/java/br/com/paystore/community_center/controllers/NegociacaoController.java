package br.com.paystore.community_center.controllers;


import br.com.paystore.community_center.dto.NegociacaoRequestDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import br.com.paystore.community_center.services.NegociacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/negociacoes")
@RequiredArgsConstructor
public class NegociacaoController {

    private final NegociacaoService service;

    @PostMapping
    public ResponseEntity<NegociacaoResponseDTO> negociar(@RequestBody NegociacaoRequestDTO dto) {
        NegociacaoResponseDTO response = service.negociar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NegociacaoResponseDTO>> listarPorCentroEData(
            @RequestParam String centroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde
    ) {
        List<NegociacaoResponseDTO> negociacoes = service.listarPorCentroEData(centroId, desde);
        return ResponseEntity.ok(negociacoes);
    }
}