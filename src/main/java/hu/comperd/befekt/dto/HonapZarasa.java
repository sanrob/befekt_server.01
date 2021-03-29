package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;

public class HonapZarasa {
  /** Azonosító. */
  private String        id;
  /** Könyvelés időszak - Hónmap. */
  private String        hozHonap;
  /** Lezárás jelző. */
  private boolean       hozZarasJel;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hozMddat;

  public HonapZarasa() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getHozHonap() {
    return this.hozHonap;
  }

  public void setHozHonap(final String pHozHonap) {
    this.hozHonap = pHozHonap;
  }

  public boolean isHozZarasJel() {
    return this.hozZarasJel;
  }

  public void setHozZarasJel(final boolean pHozZarasJel) {
    this.hozZarasJel = pHozZarasJel;
  }

  public ZonedDateTime getHozMddat() {
    return this.hozMddat;
  }

  public void setHozMddat(final ZonedDateTime pHozMddat) {
    this.hozMddat = pHozMddat;
  }
}
