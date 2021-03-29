package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.SzamlaOsszesenCol;

public interface SzamlaOsszesenRepository extends MongoRepository<SzamlaOsszesenCol, String> {
  public List<SzamlaOsszesenCol> findAllByOrderBySzoSzaAzonAsc();
}
