package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.BefektFajtaCol;

public interface BefektFajtaRepository extends MongoRepository<BefektFajtaCol, String> {

  public List<BefektFajtaCol> findAllByOrderByBffKod();

  public List<BefektFajtaCol> findAllByOrderByBffKodDesc();

  public BefektFajtaCol findByBffKod(String bffKod);

  public List<BefektFajtaCol> findByBffSzamla(String bffSzamla);

  public List<BefektFajtaCol> findByBffJutSzla(String bffSzamla);
}
