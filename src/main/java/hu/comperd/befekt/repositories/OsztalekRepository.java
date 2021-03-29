package hu.comperd.befekt.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.OsztalekCol;

public interface OsztalekRepository extends MongoRepository<OsztalekCol, String> {

  public OsztalekCol findByOszBefektetesId(String befektId);
}
