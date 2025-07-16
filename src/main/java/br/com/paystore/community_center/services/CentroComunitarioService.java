package br.com.paystore.community_center.services;

import br.com.paystore.community_center.dto.CentroComunitarioRequestDTO;
import br.com.paystore.community_center.dto.CentroComunitarioResponseDTO;
import br.com.paystore.community_center.model.CentroComunitario;
import br.com.paystore.community_center.repositories.CentroComunitarioRepository;
import br.com.paystore.community_center.util.CentroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CentroComunitarioService {

    private final CentroComunitarioRepository repository;
    private final CentroMapper mapper;

    public CentroComunitarioResponseDTO criarCentro(CentroComunitarioRequestDTO dto) {
        CentroComunitario centro = mapper.paraEntidade(dto);
        centro.setCriadoEm(LocalDateTime.now());
        centro.setAtualizadoEm(LocalDateTime.now());
        centro.setOcupacaoAtual(dto.getOcupacaoAtual() != null ? dto.getOcupacaoAtual() : 0);
        return mapper.paraResposta(repository.save(centro));
    }

    public List<CentroComunitarioResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public CentroComunitarioResponseDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(mapper::paraResposta)
                .orElseThrow(() -> new RuntimeException("Centro não encontrado"));
    }

    public CentroComunitarioResponseDTO atualizar(String id, CentroComunitarioRequestDTO dto) {
        CentroComunitario existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro não encontrado"));

        CentroComunitario atualizado = CentroComunitario.builder()
                .id(existente.getId())
                .nome(dto.getNome())
                .endereco(dto.getEndereco())
                .localizacao(dto.getLocalizacao())
                .capacidadeMaxima(dto.getCapacidadeMaxima())
                .ocupacaoAtual(existente.getOcupacaoAtual())
                .recursos(mapper.paraEntidade(dto.getRecursos()))
                .criadoEm(existente.getCriadoEm())
                .atualizadoEm(LocalDateTime.now())
                .build();

        return mapper.paraResposta(repository.save(atualizado));
    }

    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Centro não encontrado");
        }
        repository.deleteById(id);
    }

    public void atualizarOcupacao(String id, int novaOcupacao) {
        CentroComunitario centro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro não encontrado"));

        centro.setOcupacaoAtual(novaOcupacao);
        centro.setAtualizadoEm(LocalDateTime.now());

        repository.save(centro);
    }
}

