package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Bevetel {
  /** Azonosító. */
  private String        id;
  /** Bevétel dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     bevDatum;
  /** Bevétel azonosítója. */
  private String        bevAzon;
  /** Bevétel elnevezése. */
  private String        bevSzoveg;
  /** Bevétel összege. */
  private double        bevOsszeg;
  /** Bevételi számla azonosítója. */
  private String        bevSzamla;
  /** Bevételi számla megnevezése. */
  private String        bevSzamlaNev;
  /** Számlaforgalomba való könyvelés megtörtént-e. */
  private boolean       bevSzlaKonyv;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime bevMddat;

  public Bevetel() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public LocalDate getBevDatum() {
    return this.bevDatum;
  }

  public void setBevDatum(final LocalDate pBevDatum) {
    this.bevDatum = pBevDatum;
  }

  public String getBevAzon() {
    return this.bevAzon;
  }

  public void setBevAzon(final String pBevAzon) {
    this.bevAzon = pBevAzon;
  }

  public String getBevSzoveg() {
    return this.bevSzoveg;
  }

  public void setBevSzoveg(final String pBevSzoveg) {
    this.bevSzoveg = pBevSzoveg;
  }

  public double getBevOsszeg() {
    return this.bevOsszeg;
  }

  public void setBevOsszeg(final double pBevOsszeg) {
    this.bevOsszeg = pBevOsszeg;
  }

  public String getBevSzamla() {
    return this.bevSzamla;
  }

  public void setBevSzamla(final String pBevSzamla) {
    this.bevSzamla = pBevSzamla;
  }

  public String getBevSzamlaNev() {
    return this.bevSzamlaNev;
  }

  public void setBevSzamlaNev(final String pBevSzamlaNev) {
    this.bevSzamlaNev = pBevSzamlaNev;
  }

  public boolean isBevSzlaKonyv() {
    return this.bevSzlaKonyv;
  }

  public void setBevSzlaKonyv(final boolean pBevSzlaKonyv) {
    this.bevSzlaKonyv = pBevSzlaKonyv;
  }

  public ZonedDateTime getBevMddat() {
    return this.bevMddat;
  }

  public void setBevMddat(final ZonedDateTime pBevMddat) {
    this.bevMddat = pBevMddat;
  }
}
