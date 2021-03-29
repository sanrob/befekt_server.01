package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.NyitasZarasParokCol;

public interface NyitasZarasParokRepository extends MongoRepository<NyitasZarasParokCol, String> {

  public List<NyitasZarasParokCol> findByParZarAzon(String parZarAzon);

  public List<NyitasZarasParokCol> findByParNyitAzon(String parNyitAzon);
}
