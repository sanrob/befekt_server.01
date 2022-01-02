package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.HavKiadTeny;

@Document(collection = "havi_kiadas_teny")
public class HavKiadTenyCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Időszak. */
  private String        hkmHonap;
  /** Megnevezése. */
  private String        hkmMegnev;
  /** A kiadáshoz tartozó számla azonosítója. */
  private String        hkmSzamla;
  /** Tervezett kiadás. */
  private double        hkmTervOsszeg;
  /** Tényleges kiadás. */
  private double        hkmTenyOsszeg;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hkmMddat;

  public HavKiadTenyCol() {
  }

  public HavKiadTenyCol(final HavKiadTeny havKiadTeny) {
    this.id = havKiadTeny.getId();
    this.hkmHonap = havKiadTeny.getHkmHonap();
    this.hkmMegnev = havKiadTeny.getHkmMegnev();
    this.hkmSzamla = havKiadTeny.getHkmSzamla();
    this.hkmTervOsszeg = havKiadTeny.getHkmTervOsszeg();
    this.hkmTenyOsszeg = havKiadTeny.getHkmTenyOsszeg();
    this.hkmMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getHkmHonap() {
    return this.hkmHonap;
  }

  public void setHkmHonap(final String pHkmHonap) {
    this.hkmHonap = pHkmHonap;
  }

  public String getHkmMegnev() {
    return this.hkmMegnev;
  }

  public void setHkmMegnev(final String pHkmMegnev) {
    this.hkmMegnev = pHkmMegnev;
  }

  public String getHkmSzamla() {
    return this.hkmSzamla;
  }

  public void setHkmSzamla(final String pHkmSzamla) {
    this.hkmSzamla = pHkmSzamla;
  }

  public double getHkmTervOsszeg() {
    return this.hkmTervOsszeg;
  }

  public void setHkmTervOsszeg(final double pHkmTervOsszeg) {
    this.hkmTervOsszeg = pHkmTervOsszeg;
  }

  public double getHkmTenyOsszeg() {
    return this.hkmTenyOsszeg;
  }

  public void setHkmTenyOsszeg(final double pHkmTenyOsszeg) {
    this.hkmTenyOsszeg = pHkmTenyOsszeg;
  }

  public ZonedDateTime getHkmMddat() {
    return this.hkmMddat;
  }

  public void setHkmMddat(final ZonedDateTime pHkmMddat) {
    this.hkmMddat = pHkmMddat;
  }

  @Override
  public String toString() {
    return "HavKiadTeny["
        + "id=" + this.id
        + ", hkm_honap=" + this.hkmHonap
        + ", hkm_megnev=" + this.hkmMegnev
        + ", hkm_szamla=" + this.hkmSzamla
        + ", hkm_terv_osszeg=" + this.hkmTervOsszeg
        + ", hkm_teny_osszeg=" + this.hkmTenyOsszeg
        + ", hkm_mddat=" + this.hkmMddat
        + "]";
  }
}
