package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.OsztalekCol;

public interface OsztalekRepository extends MongoRepository<OsztalekCol, String> {

  public List<OsztalekCol> findAllByOszBefektetesIdOrderByOszDatumDescOszAzonDesc(String befektId);

  public List<OsztalekCol> findAllByOszAdoSzamla(String szaKod);
}
