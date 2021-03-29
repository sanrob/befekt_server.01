package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.BefektZarasCol;

public interface BefektZarasRepository extends MongoRepository<BefektZarasCol, String> {

  public BefektZarasCol findByBezAzon(String bezAzon);

  public List<BefektZarasCol> findAllByBezEvOrderByBezDatumDesc(int bezEv);

  public List<BefektZarasCol> findByBezBffKod(String bffKod);
}
