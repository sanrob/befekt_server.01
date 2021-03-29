package hu.comperd.befekt.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.HataridosElszamolasCol;

public interface HataridosElszamolasRepository extends MongoRepository<HataridosElszamolasCol, String> {

  public List<HataridosElszamolasCol> findAllByHatNyitoAzon(String nyitAzon);

  public List<HataridosElszamolasCol> findAllByHatNyitoId(String nyitoId);
}
