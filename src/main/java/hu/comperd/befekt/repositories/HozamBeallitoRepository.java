package hu.comperd.befekt.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.HozamBeallitoCol;

public interface HozamBeallitoRepository extends MongoRepository<HozamBeallitoCol, String> {

  public HozamBeallitoCol findByHobParositasId(String parAzon);
}
