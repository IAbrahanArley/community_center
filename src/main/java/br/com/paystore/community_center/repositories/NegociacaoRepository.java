package br.com.paystore.community_center.repositories;

import br.com.paystore.community_center.model.Negociacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NegociacaoRepository extends MongoRepository<Negociacao, String> {
    List<Negociacao> findByCentroOrigemIdOrCentroDestinoIdEDataHora(
            String origemId,
            String destinoId,
            LocalDateTime inicio,
            LocalDateTime fim
    );
}