package hu.comperd.befekt.dto;

import hu.comperd.befekt.collections.KonyvelesiEvCol;

public class KonyvelesiEv {
  /** Azonosító. */
  private String id;
  /** Könyvelési év. */
  private String konEv;

  public KonyvelesiEv(final KonyvelesiEvCol konyvelesiEvCol) {
    this.id = konyvelesiEvCol.getId();
    this.konEv = konyvelesiEvCol.getKonEv();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getKonEv() {
    return this.konEv;
  }

  public void setKonEv(final String pKonEv) {
    this.konEv = pKonEv;
  }
}
