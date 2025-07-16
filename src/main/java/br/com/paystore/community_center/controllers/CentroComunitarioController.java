package br.com.paystore.community_center.controllers;

import br.com.paystore.community_center.dto.CentroComunitarioRequestDTO;
import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.services.CentroComunitarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centros")
@RequiredArgsConstructor
public class CentroComunitarioController {

    private final CentroComunitarioService service;


    @PostMapping
    public ResponseEntity<CentroComunitarioResponseDTO> criarCentro(
            @RequestBody CentroComunitarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCentro(dto));
    }

    @GetMapping
    public ResponseEntity<List<CentroComunitarioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroComunitarioResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroComunitarioResponseDTO> atualizar(
            @PathVariable String id,
            @RequestBody CentroComunitarioRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ocupacao")
    public ResponseEntity<Void> atualizarOcupacao(
            @PathVariable String id,
            @RequestParam int ocupacao) {
        service.atualizarOcupacao(id, ocupacao);
        return ResponseEntity.noContent().build();
    }
}
