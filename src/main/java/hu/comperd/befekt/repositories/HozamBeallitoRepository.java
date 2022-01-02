package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.HozamBeallitoCol;

public interface HozamBeallitoRepository extends MongoRepository<HozamBeallitoCol, String> {

  public HozamBeallitoCol findByHobParositasId(String parAzon);

  public List<HozamBeallitoCol> findAllByHobTartSzamla(String szaKod);

  public List<HozamBeallitoCol> findAllByHobKovSzamla(String szaKod);
}
