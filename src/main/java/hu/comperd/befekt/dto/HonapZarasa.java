package hu.comperd.befekt.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import hu.comperd.befekt.collections.HonapZarasaCol;

public class HonapZarasa {
  /** Azonosító. */
  private String        id;
  /** Könyvelés időszak - Hónmap. */
  private String        hozHonap;
  /** Lezárás jelző. */
  private boolean       hozZarasJel;
  /** Általános kiadás tételek generálva vannak-e. */
  private boolean       hozAltKiadGeneralva;
  /** Generált kiadás tétel azonosítók. */
  private String        hozAltKiadAzonositok;
  /** Utolsó módosítás ideje. */
  private LocalDateTime hozMddat;

  public HonapZarasa() {
    //
  }

  public HonapZarasa(final HonapZarasaCol honapZarasaCol) {
    this.id = honapZarasaCol.getId();
    this.hozHonap = honapZarasaCol.getHozHonap();
    this.hozZarasJel = honapZarasaCol.isHozZarasJel();
    this.hozAltKiadGeneralva = honapZarasaCol.isHozAltKiadGeneralva();
    this.hozAltKiadAzonositok = honapZarasaCol.getHozAltKiadAzonositok();
    this.hozMddat = honapZarasaCol.getHozMddat().withZoneSameInstant(ZoneId.of("Europe/Budapest")).toLocalDateTime();
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

  public boolean isHozAltKiadGeneralva() {
    return this.hozAltKiadGeneralva;
  }

  public void setHozAltKiadGeneralva(final boolean pHozAltKiadGeneralva) {
    this.hozAltKiadGeneralva = pHozAltKiadGeneralva;
  }

  public String getHozAltKiadAzonositok() {
    return this.hozAltKiadAzonositok;
  }

  public void setHozAltKiadAzonositok(final String pHozAltKiadAzonositok) {
    this.hozAltKiadAzonositok = pHozAltKiadAzonositok;
  }

  public LocalDateTime getHozMddat() {
    return this.hozMddat;
  }

  public void setHozMddat(final LocalDateTime pHozMddat) {
    this.hozMddat = pHozMddat;
  }
}
