package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.util.List;
import hu.comperd.befekt.collections.BefektetesCol;

public interface BefektEgyenlegCustomRepo {

  List<BefektetesCol> findAllTetelNyitottAdottNapon(final LocalDate pLekDatum);
}
