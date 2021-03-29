package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Osztalek {
  /** Azonosító. */
  private String        id;
  /** Befektetés azonosító. */
  private String        oszBefektetesId;
  /** Osztalék tétel azonosítója. */
  private String        oszAzon;
  /** Osztalék dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     oszDatum;
  /** Befektetés darabszáma. */
  private double        oszBefDarab;
  /** Osztalék mértéke. */
  private double        oszMertek;
  /** Osztalék összege. */
  private double        oszOsszeg;
  /** Adó %-a. */
  private double        oszAdoSzaz;
  /** Adó mértéke. */
  private double        oszAdo;
  /** Adó számla. */
  private String        oszAdoSzamla;
  /** Adó számla neve. */
  private String        oszAdoSzamlaNev;
  /** Könyvelve van-e. */
  private boolean       oszKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime oszMddat;

  public Osztalek() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getOszBefektetesId() {
    return this.oszBefektetesId;
  }

  public void setOszBefektetesId(final String pOszBefektetesId) {
    this.oszBefektetesId = pOszBefektetesId;
  }

  public String getOszAzon() {
    return this.oszAzon;
  }

  public void setOszAzon(final String poszAzon) {
    this.oszAzon = poszAzon;
  }

  public LocalDate getOszDatum() {
    return this.oszDatum;
  }

  public void setOszDatum(final LocalDate pOszDatum) {
    this.oszDatum = pOszDatum;
  }

  public double getOszBefDarab() {
    return this.oszBefDarab;
  }

  public void setOszBefDarab(final double pOszBefDarab) {
    this.oszBefDarab = pOszBefDarab;
  }

  public double getOszMertek() {
    return this.oszMertek;
  }

  public void setOszMertek(final double pOszMertek) {
    this.oszMertek = pOszMertek;
  }

  public double getOszOsszeg() {
    return this.oszOsszeg;
  }

  public void setOszOsszeg(final double pOszOsszeg) {
    this.oszOsszeg = pOszOsszeg;
  }

  public double getOszAdoSzaz() {
    return this.oszAdoSzaz;
  }

  public void setOszAdoSzaz(final double pOszAdoSzaz) {
    this.oszAdoSzaz = pOszAdoSzaz;
  }

  public double getOszAdo() {
    return this.oszAdo;
  }

  public void setOszAdo(final double pOszAdo) {
    this.oszAdo = pOszAdo;
  }

  public String getOszAdoSzamla() {
    return this.oszAdoSzamla;
  }

  public void setOszAdoSzamla(final String pOszAdoSzamla) {
    this.oszAdoSzamla = pOszAdoSzamla;
  }

  public String getOszAdoSzamlaNev() {
    return this.oszAdoSzamlaNev;
  }

  public void setOszAdoSzamlaNev(final String pOszAdoSzamlaNev) {
    this.oszAdoSzamlaNev = pOszAdoSzamlaNev;
  }

  public boolean isOszKonyvelve() {
    return this.oszKonyvelve;
  }

  public void setOszKonyvelve(final boolean pOszKonyvelve) {
    this.oszKonyvelve = pOszKonyvelve;
  }

  public ZonedDateTime getOszMddat() {
    return this.oszMddat;
  }

  public void setOszMddat(final ZonedDateTime pOszMddat) {
    this.oszMddat = pOszMddat;
  }
}
