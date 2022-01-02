package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.HonapZarasa;

@Document(collection = "honap_zarasa")
public class HonapZarasaCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Könyvelés időszak - Hónap. */
  private String        hozHonap;
  /** Lezárás jelző. */
  private boolean       hozZarasJel;
  /** Általános kiadás tételek generálva vannak-e. */
  private boolean       hozAltKiadGeneralva;
  /** Generált kiadás tétel azonosítók. */
  private String        hozAltKiadAzonositok;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hozMddat;

  public HonapZarasaCol() {
  }

  public HonapZarasaCol(final HonapZarasa honapZarasa) {
    this.id = honapZarasa.getId();
    this.hozHonap = honapZarasa.getHozHonap();
    this.hozZarasJel = honapZarasa.isHozZarasJel();
    this.hozAltKiadGeneralva = honapZarasa.isHozAltKiadGeneralva();
    this.hozAltKiadAzonositok = honapZarasa.getHozAltKiadAzonositok();
    this.hozMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public HonapZarasaCol(final String pHozHonap) {
    this.hozHonap = pHozHonap;
    this.hozZarasJel = false;
    this.hozAltKiadGeneralva = false;
    this.hozAltKiadAzonositok = "";
    this.hozMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  public ZonedDateTime getHozMddat() {
    return this.hozMddat;
  }

  public void setHozMddat(final ZonedDateTime pHozMddat) {
    this.hozMddat = pHozMddat;
  }

  @Override
  public String toString() {
    return "HonapZarasa["
        + "_id=" + this.id
        + ", hoz_honap=" + this.hozHonap
        + ", hoz_alt_kiad_generalva=" + this.isHozAltKiadGeneralva()
        + ", hoz_alt_kiad_azonositok=" + this.getHozAltKiadAzonositok()
        + ", hoz_zaras_jel=" + this.isHozZarasJel()
        + ", hoz_mddat=" + this.hozMddat
        + "]";
  }
}
