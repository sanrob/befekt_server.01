package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.SzamlaCol;

public interface SzamlaRepository extends MongoRepository<SzamlaCol, String> {

  public List<SzamlaCol> findAllByOrderBySzaKod();

  public SzamlaCol findBySzaKod(String szaKod);

  public List<SzamlaCol> findBySzaSzlatipOrderBySzaMegnevAsc(String szaSzlatip);

  public List<SzamlaCol> findBySzaDeviza(String szaDeviza);

  public List<SzamlaCol> findBySzaPenzint(String szaPenzint);
}
