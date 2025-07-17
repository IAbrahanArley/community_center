package br.com.paystore.community_center.controllers;


import br.com.paystore.community_center.dto.NegociacaoRequestDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import br.com.paystore.community_center.services.NegociacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/negociacoes")
@RequiredArgsConstructor
public class NegociacaoController {

    private final NegociacaoService service;

    @PostMapping
    public NegociacaoResponseDTO negociar(@RequestBody NegociacaoRequestDTO dto) {
        return service.negociar(dto);
    }

    @GetMapping
    public List<NegociacaoResponseDTO> listarPorCentroEData(
            @RequestParam String centroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde
    ) {
        return service.listarPorCentroEData(centroId, desde);
    }
}