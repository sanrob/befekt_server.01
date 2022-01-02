package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.KamatCol;

public interface KamatRepository extends MongoRepository<KamatCol, String> {

  public List<KamatCol> findAllByKamBefektetesIdOrderByKamDatumDescKamAzonDesc(String befektId);

  public List<KamatCol> findAllByKamAdoSzamla(String szaKod);
}
