package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;

public class HavKiadTeny {
  /** Azonosító. */
  private String        id;
  /** Azonosító. */
  private String        hkmAzon;
  /** Időszak. */
  private String        hkmHonap;
  /** Sorszám. */
  private String        hkmSorszam;
  /** Megnevezése. */
  private String        hkmMegnev;
  /** A kiadáshoz tartozó számla azonosítója. */
  private String        hkmSzamla;
  /** A kiadáshoz tartozó számla megnevezése. */
  private String        hkmSzamlaNev;
  /** Tervezett összeg. */
  private double        hkmTervOsszeg;
  /** Tényleges kiadás. */
  private double        hkmTenyOsszeg;
  /** Könyvelve van-e. */
  private boolean       hkmKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hkmMddat;

  public HavKiadTeny() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getHkmAzon() {
    return this.hkmAzon;
  }

  public void setHkmAzon(final String pHkmAzon) {
    this.hkmAzon = pHkmAzon;
  }

  public String getHkmHonap() {
    return this.hkmHonap;
  }

  public void setHkmHonap(final String pHkmHonap) {
    this.hkmHonap = pHkmHonap;
  }

  public String getHkmSorszam() {
    return this.hkmSorszam;
  }

  public void setHkmSorszam(final String pHkmSorszam) {
    this.hkmSorszam = pHkmSorszam;
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

  public String getHkmSzamlaNev() {
    return this.hkmSzamlaNev;
  }

  public void setHkmSzamlaNev(final String pHkmSzamlaNev) {
    this.hkmSzamlaNev = pHkmSzamlaNev;
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

  public boolean isHkmKonyvelve() {
    return this.hkmKonyvelve;
  }

  public void setHkmKonyvelve(final boolean pHkmKonyvelve) {
    this.hkmKonyvelve = pHkmKonyvelve;
  }

  public ZonedDateTime getHkmMddat() {
    return this.hkmMddat;
  }

  public void setHkmMddat(final ZonedDateTime pHkmMddat) {
    this.hkmMddat = pHkmMddat;
  }
}
