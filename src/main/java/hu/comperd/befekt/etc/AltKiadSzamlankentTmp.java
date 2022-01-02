package hu.comperd.befekt.etc;

import hu.comperd.befekt.collections.HavKiadTenyCol;
import hu.comperd.befekt.dto.AltKiadSzamlankent;
import hu.comperd.befekt.repositories.HavKiadTervRepository;

public class AltKiadSzamlankentTmp {
  /** Számla kódja. */
  private String tmpSzaKod;
  /** Kiadás kódja. */
  private String tmpKiadKod;
  /** Tervezett kiadás összege. */
  private double tmpTervOsszeg;
  /** Tényleges kiadás összege. */
  private double tmpTenyOsszeg;

  public AltKiadSzamlankentTmp() {
    //
  }

  public AltKiadSzamlankentTmp(final HavKiadTenyCol havKiadTenyCol) {
    this.tmpSzaKod = havKiadTenyCol.getHkmSzamla();
    this.tmpKiadKod = havKiadTenyCol.getHkmMegnev();
    this.tmpTenyOsszeg = havKiadTenyCol.getHkmTenyOsszeg();
  }

  public AltKiadSzamlankentTmp(final AltKiadSzamlankent szamlankent) {
    this.tmpTenyOsszeg = szamlankent.getAksTenyOsszeg();
    this.tmpTervOsszeg = szamlankent.getAksTervOsszeg();
  }

  public AltKiadSzamlankentTmp setDatas(final HavKiadTervRepository tervRepo) {
    this.tmpTervOsszeg = tervRepo.findByHktMegnev(this.tmpKiadKod).getHktOsszeg();
    return this;
  }

  public String getTmpSzaKod() {
    return this.tmpSzaKod;
  }

  public void setTmpSzaKod(final String pTmpSzaKod) {
    this.tmpSzaKod = pTmpSzaKod;
  }

  public String getTmpKiadKod() {
    return this.tmpKiadKod;
  }

  public void setTmpKiadKod(final String pTmpKiadKod) {
    this.tmpKiadKod = pTmpKiadKod;
  }

  public double getTmpTervOsszeg() {
    return this.tmpTervOsszeg;
  }

  public void setTmpTervOsszeg(final double pTmpTervOsszeg) {
    this.tmpTervOsszeg = pTmpTervOsszeg;
  }

  public double getTmpTenyOsszeg() {
    return this.tmpTenyOsszeg;
  }

  public void setTmpTenyOsszeg(final double pTmpTenyOsszeg) {
    this.tmpTenyOsszeg = pTmpTenyOsszeg;
  }
}
