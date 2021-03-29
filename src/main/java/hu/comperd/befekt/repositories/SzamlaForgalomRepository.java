package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.SzamlaForgalomCol;

public interface SzamlaForgalomRepository extends MongoRepository<SzamlaForgalomCol, String> {
  public List<SzamlaForgalomCol> findAllBySzfHivBizTipAndSzfHivBizAzon(final String szfHivBizTip, final String szfHivBizAzon);
}
