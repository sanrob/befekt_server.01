package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class NyitasZarasParok {
  /** Azonosító. */
  private String        id;
  /** Nyitó tétel azonosítója. */
  private String        parNyitAzon;
  /** Nyitó darabszám. */
  private double        parNyitDarab;
  /** Eddig párosított. */
  private double        parParositott;
  /** Záró tétel azonosítója. */
  private String        parZarAzon;
  /** Párosított darabszám. */
  private double        parDarab;
  /** Nyitás dátuma. */
  private LocalDate     parNyitDatum;
  /** Nyitás dátuma. */
  private LocalDate     parZarDatum;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime parMddat;

  public NyitasZarasParok() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getParNyitAzon() {
    return this.parNyitAzon;
  }

  public void setParNyitAzon(final String pParNyitAzon) {
    this.parNyitAzon = pParNyitAzon;
  }

  public double getParNyitDarab() {
    return this.parNyitDarab;
  }

  public void setParNyitDarab(final double pParNyitDarab) {
    this.parNyitDarab = pParNyitDarab;
  }

  public double getParParositott() {
    return this.parParositott;
  }

  public void setParParositott(final double pParParositott) {
    this.parParositott = pParParositott;
  }

  public String getParZarAzon() {
    return this.parZarAzon;
  }

  public void setParZarAzon(final String pParZarAzon) {
    this.parZarAzon = pParZarAzon;
  }

  public double getParDarab() {
    return this.parDarab;
  }

  public void setParDarab(final double pParDarab) {
    this.parDarab = pParDarab;
  }

  public LocalDate getParNyitDatum() {
    return this.parNyitDatum;
  }

  public void setParNyitDatum(final LocalDate pParNyitDatum) {
    this.parNyitDatum = pParNyitDatum;
  }

  public LocalDate getParZarDatum() {
    return this.parZarDatum;
  }

  public void setParZarDatum(final LocalDate pParZarDatum) {
    this.parZarDatum = pParZarDatum;
  }

  public ZonedDateTime getParMddat() {
    return this.parMddat;
  }

  public void setParMddat(final ZonedDateTime pParMddat) {
    this.parMddat = pParMddat;
  }
}
