package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.HavKiadTervCol;

public interface HavKiadTervRepository extends MongoRepository<HavKiadTervCol, String> {

  public List<HavKiadTervCol> findAllByOrderByHktSorszamAsc();

  public HavKiadTervCol findByHktMegnev(String hktMegnev);

  public List<HavKiadTervCol> findAllByhktSzamla(String hktSzamla);
}
