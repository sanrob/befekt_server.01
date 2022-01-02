package hu.comperd.befekt.dto;

import java.util.Map;
import hu.comperd.befekt.collections.HavKiadTenyCol;
import hu.comperd.befekt.collections.SzamlaCol;
import hu.comperd.befekt.etc.AltKiadSzamlankentTmp;
import hu.comperd.befekt.repositories.SzamlaRepository;
import hu.comperd.befekt.repositories.SzamlavezetoRepository;

public class AltKiadSzamlankent {
  /** Számla kódja. */
  private String aksSzaKod;
  /** Számla megnevezése. */
  private String aksSzaMegnev;
  /** Számlát kezelő pénzintézet. */
  private String aksSzaPenzint;
  /** Számlát kezelő pénzintézet megnevezése. */
  private String aksSzaPenzintNev;
  /** Tervezett kiadás összege. */
  private double aksTervOsszeg;
  /** Tényleges kiadás összege. */
  private double aksTenyOsszeg;

  public AltKiadSzamlankent() {
    //
  }

  public AltKiadSzamlankent(final HavKiadTenyCol havKiadTenyCol) {
    this.aksSzaKod = havKiadTenyCol.getHkmSzamla();
    this.aksTenyOsszeg = havKiadTenyCol.getHkmTenyOsszeg();
  }

  public AltKiadSzamlankent(final Map.Entry<String, AltKiadSzamlankentTmp> szamlankentTmp) {
    this.aksSzaKod = szamlankentTmp.getValue().getTmpSzaKod();
    this.aksTervOsszeg = szamlankentTmp.getValue().getTmpTervOsszeg();
    this.aksTenyOsszeg = szamlankentTmp.getValue().getTmpTenyOsszeg();
  }

  public AltKiadSzamlankent setDatas(final SzamlaRepository szaRepo, final SzamlavezetoRepository szlaVezRepo) {
    final SzamlaCol szamlaCol = szaRepo.findBySzaKod(this.aksSzaKod);
    this.aksSzaMegnev = szamlaCol.getSzaMegnev();
    this.aksSzaPenzint = szamlaCol.getSzaPenzint();
    this.aksSzaPenzintNev = szlaVezRepo.findBySzvKod(this.aksSzaPenzint).getSzvMegnev();
    return this;
  }

  public AltKiadSzamlankent setDatasOsszesen() {
    this.aksSzaMegnev = "Összesen";
    return this;
  }

  public String getAksSzaKod() {
    return this.aksSzaKod;
  }

  public void setAksSzaKod(final String pAksSzaKod) {
    this.aksSzaKod = pAksSzaKod;
  }

  public String getAksSzaMegnev() {
    return this.aksSzaMegnev;
  }

  public void setAksSzaMegnev(final String pAksSzaMegnev) {
    this.aksSzaMegnev = pAksSzaMegnev;
  }

  public String getAksSzaPenzint() {
    return this.aksSzaPenzint;
  }

  public void setAksSzaPenzint(final String pAksSzaPenzint) {
    this.aksSzaPenzint = pAksSzaPenzint;
  }

  public String getAksSzaPenzintNev() {
    return this.aksSzaPenzintNev;
  }

  public void setAksSzaPenzintNev(final String pAksSzaPenzintNev) {
    this.aksSzaPenzintNev = pAksSzaPenzintNev;
  }

  public double getAksTervOsszeg() {
    return this.aksTervOsszeg;
  }

  public void setAksTervOsszeg(final double pAksTervOsszeg) {
    this.aksTervOsszeg = pAksTervOsszeg;
  }

  public double getAksTenyOsszeg() {
    return this.aksTenyOsszeg;
  }

  public void setAksTenyOsszeg(final double pAksTenyOsszeg) {
    this.aksTenyOsszeg = pAksTenyOsszeg;
  }
}
