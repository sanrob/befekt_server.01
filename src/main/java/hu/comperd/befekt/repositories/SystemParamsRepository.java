package hu.comperd.befekt.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.SystemParamsCol;

public interface SystemParamsRepository extends MongoRepository<SystemParamsCol, String> {
  //
}
