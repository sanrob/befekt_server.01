package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.DomainCol;

public interface DomainRepository extends MongoRepository<DomainCol, String> {

  public List<DomainCol> findByDomCsoportKodOrderByDomKodAsc(String domCsoportKod);

  public DomainCol findByDomCsoportKodAndDomKod(String domCsoportKod, String domKod);
}
