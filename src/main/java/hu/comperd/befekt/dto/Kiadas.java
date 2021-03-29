package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Kiadas {
  /** Azonosító. */
  private String        id;
  /** Kiadás dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     kiaDatum;
  /** Kiadás azonosítója. */
  private String        kiaAzon;
  /** Kiadás szövege. */
  private String        kiaSzoveg;
  /** Kiadás összege. */
  private double        kiaOsszeg;
  /** Kiadási számla azonosítója. */
  private String        kiaSzamla;
  /** Kiadási számla megnevezése. */
  private String        kiaSzamlaNev;
  /** Kiadás típusa. */
  private String        kiaTipus;
  /** Kiadás típusának megnevezése. */
  private String        kiaTipusNev;
  /** Számlaforgalomba való könyvelés megtörtént-e. */
  private boolean       kiaSzlaKonyv;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime kiaMddat;

  public Kiadas() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public LocalDate getKiaDatum() {
    return this.kiaDatum;
  }

  public void setKiaDatum(final LocalDate pKiaDatum) {
    this.kiaDatum = pKiaDatum;
  }

  public String getKiaAzon() {
    return this.kiaAzon;
  }

  public void setKiaAzon(final String pKiaAzon) {
    this.kiaAzon = pKiaAzon;
  }

  public String getKiaSzoveg() {
    return this.kiaSzoveg;
  }

  public void setKiaSzoveg(final String pKiaSzoveg) {
    this.kiaSzoveg = pKiaSzoveg;
  }

  public double getKiaOsszeg() {
    return this.kiaOsszeg;
  }

  public void setKiaOsszeg(final double pKiaOsszeg) {
    this.kiaOsszeg = pKiaOsszeg;
  }

  public String getKiaSzamla() {
    return this.kiaSzamla;
  }

  public void setKiaSzamla(final String pKiaSzamla) {
    this.kiaSzamla = pKiaSzamla;
  }

  public String getKiaSzamlaNev() {
    return this.kiaSzamlaNev;
  }

  public void setKiaSzamlaNev(final String pKiaSzamlaNev) {
    this.kiaSzamlaNev = pKiaSzamlaNev;
  }

  public String getKiaTipus() {
    return this.kiaTipus;
  }

  public void setKiaTipus(final String pKiaTipus) {
    this.kiaTipus = pKiaTipus;
  }

  public String getKiaTipusNev() {
    return this.kiaTipusNev;
  }

  public void setKiaTipusNev(final String pKiaTipusNev) {
    this.kiaTipusNev = pKiaTipusNev;
  }

  public boolean isKiaSzlaKonyv() {
    return this.kiaSzlaKonyv;
  }

  public void setKiaSzlaKonyv(final boolean pKiaSzlaKonyv) {
    this.kiaSzlaKonyv = pKiaSzlaKonyv;
  }

  public ZonedDateTime getKiaMddat() {
    return this.kiaMddat;
  }

  public void setKiaMddat(final ZonedDateTime pKiaMddat) {
    this.kiaMddat = pKiaMddat;
  }
}
