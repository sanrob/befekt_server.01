package hu.comperd.befekt.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import hu.comperd.befekt.collections.HonapZarasaCol;

public interface HonapZarasaRepository extends MongoRepository<HonapZarasaCol, String> {

  public HonapZarasaCol findOneByHozHonap(String hozHonap);
}
