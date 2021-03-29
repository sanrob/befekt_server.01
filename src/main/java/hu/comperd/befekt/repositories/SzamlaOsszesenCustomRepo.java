package hu.comperd.befekt.repositories;

import java.time.LocalDate;
import java.util.List;
import hu.comperd.befekt.collections.SzamlaOsszesenCol;

public interface SzamlaOsszesenCustomRepo {

  List<SzamlaOsszesenCol> findAllBySzakodAndNotBeforeForgDat(final String pSzakod, final LocalDate pForgdat);
}
