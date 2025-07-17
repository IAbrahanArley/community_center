package br.com.paystore.community_center.controllers;

import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.dto.MediaRecursosDTO;
import br.com.paystore.community_center.dto.NegociacaoResponseDTO;
import br.com.paystore.community_center.services.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController implements RelatorioApi {

    private final RelatorioService relatorioService;

    @GetMapping("/centros-alta-ocupacao")
    public ResponseEntity<List<CentroComunitarioResponseDTO>> centrosAltaOcupacao() {
        return ResponseEntity.ok(relatorioService.listarCentrosComAltaOcupacao());
    }

    @GetMapping("/media-recursos")
    public ResponseEntity<MediaRecursosDTO> mediaRecursos() {
        return ResponseEntity.ok(relatorioService.calcularMediaRecursosPorCentro());
    }

    @GetMapping("/historico-negociacoes")
    public ResponseEntity<List<NegociacaoResponseDTO>> historicoNegociacoes(
            @RequestParam String centroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        return ResponseEntity.ok(relatorioService.listarNegociacoesPorCentro(centroId, inicio, fim));
    }
}
