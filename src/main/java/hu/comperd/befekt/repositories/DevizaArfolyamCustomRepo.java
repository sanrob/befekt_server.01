package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import hu.comperd.befekt.collections.DevizaArfolyamCol;

public interface DevizaArfolyamCustomRepo {

  DevizaArfolyamCol findMirolDevKodAndMireDevKodNapiArfolyam(final String pMirolDevKod,
                                                             final String pMireDevKod,
                                                             final LocalDate pLekDatum);
}
