package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Bevetel;

@Document(collection = "bevetelek")
public class BevetelCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Bevétel dátuma. */
  private LocalDate     bevDatum;
  /** Bevétel éve. */
  private int           bevEv;
  /** Bevétel azonosítója. */
  private String        bevAzon;
  /** Bevétel elnevezése. */
  private String        bevSzoveg;
  /** Bevétel összege. */
  private double        bevOsszeg;
  /** Bevételi számla azonosítója. */
  private String        bevSzamla;
  /** Számlaforgalomba való könyvelés megtörtént-e. */
  private boolean       bevSzlaKonyv;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime bevMddat;

  public BevetelCol() {
  }

  public BevetelCol(final Bevetel bevetel) {
    this.id = bevetel.getId();
    this.bevDatum = bevetel.getBevDatum();
    this.bevEv = this.bevDatum.getYear();
    this.bevSzoveg = bevetel.getBevSzoveg();
    this.bevOsszeg = bevetel.getBevOsszeg();
    this.bevSzamla = bevetel.getBevSzamla();
    this.bevMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  public int getBevEv() {
    return this.bevEv;
  }

  public void setBevEv(final int pBevEv) {
    this.bevEv = pBevEv;
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

  @Override
  public String toString() {
    return "Bevetel["
        + "id=" + this.id
        + ", bev_datum=" + this.bevDatum
        + ", bev_ev=" + this.bevEv
        + ", bev_azon=" + this.bevAzon
        + ", bev_szoveg=" + this.bevSzoveg
        + ", bev_osszeg=" + this.bevOsszeg
        + ", bev_szamla=" + this.bevSzamla
        + ", bev_szla_konyv=" + this.bevSzlaKonyv
        + ", bev_mddat=" + this.bevMddat
        + "]";
  }
}
