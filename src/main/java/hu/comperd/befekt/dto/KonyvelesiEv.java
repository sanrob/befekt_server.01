package hu.comperd.befekt.dto;

public class KonyvelesiEv {
  /** Azonosító. */
  private String id;
  /** Könyvelési év. */
  private String konEv;

  public KonyvelesiEv() {
    //
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
