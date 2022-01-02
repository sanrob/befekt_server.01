package hu.comperd.befekt.repositories;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import hu.comperd.befekt.collections.HonapZarasaCol;

@Repository
public class HonapZarasaCustomRepoImpl implements HonapZarasaCustomRepo {

  @Autowired
  private HonapZarasaRepository honZarRepo;

  @Override
  public List<HonapZarasaCol> findAllByHozEv(final String hozEv) {
    return this.honZarRepo.findAll().stream().filter(h -> h.getHozHonap().substring(0, 4).equals(hozEv))
        .collect(Collectors.toList());
  }
}
