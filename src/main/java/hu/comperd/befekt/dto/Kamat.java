package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Kamat {
  /** Azonosító. */
  private String        id;
  /** Befektetés azonosító. */
  private String        kamBefektetesId;
  /** Kamat tétel azonosítója. */
  private String        kamAzon;
  /** Kamat dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     kamDatum;
  /** Befektetés összege. */
  private double        kamBefOsszeg;
  /** Kamat mértéke. */
  private double        kamMertek;
  /** Kamat összege. */
  private double        kamOsszeg;
  /** Adó %-a. */
  private double        kamAdoSzaz;
  /** Adó mértéke. */
  private double        kamAdo;
  /** Adó számla. */
  private String        kamAdoSzamla;
  /** Adó számla neve. */
  private String        kamAdoSzamlaNev;
  /** Könyvelve van-e. */
  private boolean       kamKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime kamMddat;

  public Kamat() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getKamBefektetesId() {
    return this.kamBefektetesId;
  }

  public void setKamBefektetesId(final String pKamBefektetesId) {
    this.kamBefektetesId = pKamBefektetesId;
  }

  public String getKamAzon() {
    return this.kamAzon;
  }

  public void setKamAzon(final String pKamAzon) {
    this.kamAzon = pKamAzon;
  }

  public LocalDate getKamDatum() {
    return this.kamDatum;
  }

  public void setKamDatum(final LocalDate pKamDatum) {
    this.kamDatum = pKamDatum;
  }

  public double getKamBefOsszeg() {
    return this.kamBefOsszeg;
  }

  public void setKamBefOsszeg(final double pKamBefOsszeg) {
    this.kamBefOsszeg = pKamBefOsszeg;
  }

  public double getKamMertek() {
    return this.kamMertek;
  }

  public void setKamMertek(final double pKamMertek) {
    this.kamMertek = pKamMertek;
  }

  public double getKamOsszeg() {
    return this.kamOsszeg;
  }

  public void setKamOsszeg(final double pKamOsszeg) {
    this.kamOsszeg = pKamOsszeg;
  }

  public double getKamAdoSzaz() {
    return this.kamAdoSzaz;
  }

  public void setKamAdoSzaz(final double pKamAdoSzaz) {
    this.kamAdoSzaz = pKamAdoSzaz;
  }

  public double getKamAdo() {
    return this.kamAdo;
  }

  public void setKamAdo(final double pKamAdo) {
    this.kamAdo = pKamAdo;
  }

  public String getKamAdoSzamla() {
    return this.kamAdoSzamla;
  }

  public void setKamAdoSzamla(final String pKamAdoSzamla) {
    this.kamAdoSzamla = pKamAdoSzamla;
  }

  public String getKamAdoSzamlaNev() {
    return this.kamAdoSzamlaNev;
  }

  public void setKamAdoSzamlaNev(final String pKamAdoSzamlaNev) {
    this.kamAdoSzamlaNev = pKamAdoSzamlaNev;
  }

  public boolean isKamKonyvelve() {
    return this.kamKonyvelve;
  }

  public void setKamKonyvelve(final boolean pKamKonyvelve) {
    this.kamKonyvelve = pKamKonyvelve;
  }

  public ZonedDateTime getKamMddat() {
    return this.kamMddat;
  }

  public void setKamMddat(final ZonedDateTime pKamMddat) {
    this.kamMddat = pKamMddat;
  }
}
