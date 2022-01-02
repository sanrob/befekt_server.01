package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.DevizaArfolyamCol;

public interface DevizaArfolyamRepository extends MongoRepository<DevizaArfolyamCol, String> {

  public DevizaArfolyamCol findByDeaMirolDevKodAndDeaMireDevKodAndDeaArfDatum(String deaMirolDevKodAnd,
                                                                              String deaMireDevKodAnd,
                                                                              LocalDate deaArfDatum);

  public List<DevizaArfolyamCol> findByDeaMirolDevKodAndDeaMireDevKodOrderByDeaArfDatumDesc(String deaMirolDevKodAnd,
                                                                                            String deaMireDevKodAnd);
}
