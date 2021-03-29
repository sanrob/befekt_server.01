package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.BefektetesArfolyamCol;

public interface BefektetesArfolyamRepository extends MongoRepository<BefektetesArfolyamCol, String> {

  public BefektetesArfolyamCol findByBeaBefAzonAndBeaArfDatum(String beaBefAzon, LocalDate beaArfDatum);

  public List<BefektetesArfolyamCol> findByBeaBefAzonOrderByBeaArfDatumDesc(String beaBefAzon);
}
