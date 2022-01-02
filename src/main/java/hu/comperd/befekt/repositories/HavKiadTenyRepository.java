package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.HavKiadTenyCol;

public interface HavKiadTenyRepository extends MongoRepository<HavKiadTenyCol, String> {

  public List<HavKiadTenyCol> findAllByHkmHonap(String hkmHonap);

  public List<HavKiadTenyCol> findAllByHkmMegnev(String hkmMegnev);
}
