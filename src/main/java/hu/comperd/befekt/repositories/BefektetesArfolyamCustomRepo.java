package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import hu.comperd.befekt.collections.BefektetesArfolyamCol;

public interface BefektetesArfolyamCustomRepo {

  BefektetesArfolyamCol findBefKodNapiArfolyam(final String pBffKod, final LocalDate pLekDatum);
}
