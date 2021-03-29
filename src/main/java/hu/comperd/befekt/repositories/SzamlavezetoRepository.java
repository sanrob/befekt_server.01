package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.SzamlavezetoCol;

public interface SzamlavezetoRepository extends MongoRepository<SzamlavezetoCol, String> {

  public SzamlavezetoCol findBySzvKod(String szvKod);

  public List<SzamlavezetoCol> findAllByOrderBySzvKodAsc();
}
