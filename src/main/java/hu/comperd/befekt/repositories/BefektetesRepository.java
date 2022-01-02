package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.BefektetesCol;

public interface BefektetesRepository extends MongoRepository<BefektetesCol, String> {

  public BefektetesCol findByBefAzon(String befAzon);

  public List<BefektetesCol> findAllByBefEvOrderByBefDatumDesc(int befEv);

  public List<BefektetesCol> findByBefBffKod(String bffKod);

  public List<BefektetesCol> findAllByBefElszSzla(String szaKod);

  public List<BefektetesCol> findAllByBefJutSzla(String szaKod);

  public List<BefektetesCol> findAllByBefEvAndBefKonyvelve(final int befEv, final boolean befKonyvelve);
}
