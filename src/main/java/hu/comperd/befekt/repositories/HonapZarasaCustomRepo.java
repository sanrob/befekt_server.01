package hu.comperd.befekt.repositories;

import java.util.List;
import hu.comperd.befekt.collections.HonapZarasaCol;

public interface HonapZarasaCustomRepo {

  public List<HonapZarasaCol> findAllByHozEv(String hozEv);
}
