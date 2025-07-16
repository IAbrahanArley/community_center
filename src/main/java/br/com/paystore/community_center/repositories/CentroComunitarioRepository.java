package br.com.paystore.community_center.repositories;

import br.com.paystore.community_center.model.CentroComunitario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroComunitarioRepository extends MongoRepository<CentroComunitario, String> {
}

