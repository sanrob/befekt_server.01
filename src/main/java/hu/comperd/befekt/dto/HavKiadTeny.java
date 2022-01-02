package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;
import java.util.Map;
import hu.comperd.befekt.collections.HavKiadTenyCol;
import hu.comperd.befekt.collections.HavKiadTervCol;
import hu.comperd.befekt.repositories.HavKiadTervRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;

public class HavKiadTeny {
  /** Azonosító. */
  private String        id;
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
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hkmMddat;

  public HavKiadTeny() {
    //
  }

  public HavKiadTeny(final HavKiadTenyCol havKiadTenyCol) {
    this.id = havKiadTenyCol.getId();
    this.hkmHonap = havKiadTenyCol.getHkmHonap();
    this.hkmMegnev = havKiadTenyCol.getHkmMegnev();
    this.hkmSzamla = havKiadTenyCol.getHkmSzamla();
    this.hkmTervOsszeg = havKiadTenyCol.getHkmTervOsszeg();
    this.hkmTenyOsszeg = havKiadTenyCol.getHkmTenyOsszeg();
    this.hkmMddat = havKiadTenyCol.getHkmMddat();
  }

  public HavKiadTeny(final HavKiadTervCol havKiadTervCol) {
    this.hkmMegnev = havKiadTervCol.getHktMegnev();
    this.hkmSzamla = havKiadTervCol.getHktSzamla();
    this.hkmTervOsszeg = havKiadTervCol.getHktOsszeg();
    this.hkmTenyOsszeg = 0;
  }

  public HavKiadTeny(final Map.Entry<String, HavKiadTeny> tetel) {
    this.hkmTervOsszeg = tetel.getValue().getHkmTervOsszeg();
    this.hkmTenyOsszeg = tetel.getValue().getHkmTenyOsszeg();
  }

  public HavKiadTeny(final HavKiadTeny tetel) {
    this.hkmTervOsszeg = tetel.getHkmTervOsszeg();
    this.hkmTenyOsszeg = tetel.getHkmTenyOsszeg();
  }

  public HavKiadTeny setDatas(final HavKiadTervRepository haviTervRepo, final SzamlaRepository szlarepo, final String pHkmHonap) {
    if (pHkmHonap != null) {
      this.hkmHonap = pHkmHonap;
    }
    this.hkmSorszam = haviTervRepo.findByHktMegnev(this.hkmMegnev).getHktSorszam();
    this.hkmSzamlaNev = szlarepo.findBySzaKod(this.hkmSzamla).getSzaMegnev();
    return this;
  }

  public HavKiadTeny setDatas(final HavKiadTervRepository haviTervRepo, final SzamlaRepository szlarepo) {
    return this.setDatas(haviTervRepo, szlarepo, null);
  }

  public HavKiadTeny setDatas() {
    this.hkmSorszam = "000";
    this.hkmMegnev = "Összesen";
    return this;
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

  public ZonedDateTime getHkmMddat() {
    return this.hkmMddat;
  }

  public void setHkmMddat(final ZonedDateTime pHkmMddat) {
    this.hkmMddat = pHkmMddat;
  }
}
