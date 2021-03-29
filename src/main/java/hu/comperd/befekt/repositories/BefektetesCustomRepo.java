package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.util.List;
import hu.comperd.befekt.collections.BefektetesCol;

public interface BefektetesCustomRepo {

  List<BefektetesCol> findAllByBffKodDatumElottNyitott(final String pBffKod, final LocalDate pLekDatum);
}
