package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.KiadasCol;

public interface KiadasRepository extends MongoRepository<KiadasCol, String> {

  public KiadasCol findByKiaAzon(String kiaAzon);

  public List<KiadasCol> findAllByKiaEvOrderByKiaDatumDesc(int kiaEv);

  public List<KiadasCol> findByKiaSzamla(String kiaSzamla);

  public List<KiadasCol> findAllByKiaEvAndKiaSzlaKonyv(final int kiaEv, final boolean kiaSzlaKonyv);
}
