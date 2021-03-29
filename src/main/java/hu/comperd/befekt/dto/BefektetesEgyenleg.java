package hu.comperd.befekt.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BefektetesEgyenleg {
  /** Befektetés kódja. */
  private String    beeBffKod;
  /** Befektetés megnevezése. */
  private String    beeBffKodNev;
  /** Meglévő darabszám. */
  private double    beeDarab;
  /** Árfolyam dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate beeArfDatum;
  /** Árfolyam. */
  private double    beeArfolyam;
  /** Árfolyam érték. */
  private double    beeArfErtek;
  /** Befektetés devizaneme. */
  private String    beeSzaDeviza;
  /** Befektetés devizanem megnevezése. */
  private String    beeSzaDevizaNev;
  /** Deviza árfolyam. */
  private double    beeDevArfo;
  /** Alapdeviza érték. */
  private double    beeAlapdevErtek;

  public BefektetesEgyenleg() {
    //
  }

  public String getBeeBffKod() {
    return this.beeBffKod;
  }

  public void setBeeBffKod(final String pBeeBffKod) {
    this.beeBffKod = pBeeBffKod;
  }

  public String getBeeBffKodNev() {
    return this.beeBffKodNev;
  }

  public void setBeeBffKodNev(final String pBeeBffKodNev) {
    this.beeBffKodNev = pBeeBffKodNev;
  }

  public double getBeeDarab() {
    return this.beeDarab;
  }

  public void setBeeDarab(final double pBeeDarab) {
    this.beeDarab = pBeeDarab;
  }

  public LocalDate getBeeArfDatum() {
    return this.beeArfDatum;
  }

  public void setBeeArfDatum(final LocalDate pBeeArfDatum) {
    this.beeArfDatum = pBeeArfDatum;
  }

  public double getBeeArfolyam() {
    return this.beeArfolyam;
  }

  public void setBeeArfolyam(final double pBeeArfolyam) {
    this.beeArfolyam = pBeeArfolyam;
  }

  public double getBeeArfErtek() {
    return this.beeArfErtek;
  }

  public void setBeeArfErtek(final double pBeeArfErtek) {
    this.beeArfErtek = pBeeArfErtek;
  }

  public String getBeeSzaDeviza() {
    return this.beeSzaDeviza;
  }

  public void setBeeSzaDeviza(final String pBeeSzaDeviza) {
    this.beeSzaDeviza = pBeeSzaDeviza;
  }

  public String getBeeSzaDevizaNev() {
    return this.beeSzaDevizaNev;
  }

  public void setBeeSzaDevizaNev(final String pBeeSzaDevizaNev) {
    this.beeSzaDevizaNev = pBeeSzaDevizaNev;
  }

  public double getBeeDevArfo() {
    return this.beeDevArfo;
  }

  public void setBeeDevArfo(final double pBeeDevArfo) {
    this.beeDevArfo = pBeeDevArfo;
  }

  public double getBeeAlapdevErtek() {
    return this.beeAlapdevErtek;
  }

  public void setBeeAlapdevErtek(final double pBeeAlapdevErtek) {
    this.beeAlapdevErtek = pBeeAlapdevErtek;
  }
}
