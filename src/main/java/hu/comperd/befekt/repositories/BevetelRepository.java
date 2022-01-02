package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.BevetelCol;

public interface BevetelRepository extends MongoRepository<BevetelCol, String> {

  public BevetelCol findByBevAzon(String bevAzon);

  public List<BevetelCol> findAllByBevEvOrderByBevDatumDesc(final int bevEv);

  public List<BevetelCol> findByBevSzamla(String bevSzamla);

  public List<BevetelCol> findAllByBevEvAndBevSzlaKonyv(final int bevEv, final boolean bevSzlaKonyv);
}
