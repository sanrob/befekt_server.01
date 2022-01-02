package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.KonyvelesiEvCol;

public interface AlapAdatokRepository extends MongoRepository<KonyvelesiEvCol, String> {

  public List<KonyvelesiEvCol> findAllByOrderByKonEvDesc();

  public KonyvelesiEvCol findByKonEv(final String konEv);
}
