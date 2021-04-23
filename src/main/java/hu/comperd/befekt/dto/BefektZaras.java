package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BefektZaras {
  /** Azonosító. */
  private String        id;
  /** Befektetés azonosítója. */
  private String        bezAzon;
  /** Befektetés dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     bezDatum;
  /** Befektetés fajta kódja. */
  private String        bezBffKod;
  /** Befektetés fajta megnevezése. */
  private String        bezBffKodNev;
  /** Befektetett darabszám. */
  private double        bezDarab;
  /** Nyitás árfolyama. */
  private double        bezArfolyam;
  /** Nyitás árfolyama. */
  private double        bezErtek;
  /** Jutalék %-a. */
  private double        bezJutSzaz;
  /** Jutalék értéke. */
  private double        bezJutErtek;
  /** Bekerülési érték. */
  private double        bezRealErtek;
  /** Elszámolási számla kódja. */
  private String        bezElszSzla;
  /** Elszámolási számla megnevezése. */
  private String        bezElszSzlaNev;
  /** Jutalék számla kódja. */
  private String        bezJutSzla;
  /** Jutalék számla megnevezése. */
  private String        bezJutSzlaNev;
  /** Könyvelés dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     bezKonyvDat;
  /** Le van-e már könyvelve. */
  private boolean       bezKonyvelve;
  /** Párosított darabszám. */
  private double        bezParDarab;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime bezMddat;

  public BefektZaras() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getBezAzon() {
    return this.bezAzon;
  }

  public void setBezAzon(final String pBezAzon) {
    this.bezAzon = pBezAzon;
  }

  public LocalDate getBezDatum() {
    return this.bezDatum;
  }

  public void setBezDatum(final LocalDate pBezDatum) {
    this.bezDatum = pBezDatum;
  }

  public String getBezBffKod() {
    return this.bezBffKod;
  }

  public void setBezBffKod(final String pBezBffKod) {
    this.bezBffKod = pBezBffKod;
  }

  public String getBezBffKodNev() {
    return this.bezBffKodNev;
  }

  public void setBezBffKodNev(final String pBezBffKodNev) {
    this.bezBffKodNev = pBezBffKodNev;
  }

  public double getBezDarab() {
    return this.bezDarab;
  }

  public void setBezDarab(final double pBezDarab) {
    this.bezDarab = pBezDarab;
  }

  public double getBezArfolyam() {
    return this.bezArfolyam;
  }

  public void setBezArfolyam(final double pBezArfolyam) {
    this.bezArfolyam = pBezArfolyam;
  }

  public double getBezErtek() {
    return this.bezErtek;
  }

  public void setBezErtek(final double pBezErtek) {
    this.bezErtek = pBezErtek;
  }

  public double getBezJutSzaz() {
    return this.bezJutSzaz;
  }

  public void setBezJutSzaz(final double pBezJutSzaz) {
    this.bezJutSzaz = pBezJutSzaz;
  }

  public double getBezJutErtek() {
    return this.bezJutErtek;
  }

  public void setBezJutErtek(final double pBezJutErtek) {
    this.bezJutErtek = pBezJutErtek;
  }

  public double getBezRealErtek() {
    return this.bezRealErtek;
  }

  public void setBezRealErtek(final double pBezRealErtek) {
    this.bezRealErtek = pBezRealErtek;
  }

  public String getBezElszSzla() {
    return this.bezElszSzla;
  }

  public void setBezElszSzla(final String pBezElszSzla) {
    this.bezElszSzla = pBezElszSzla;
  }

  public String getBezElszSzlaNev() {
    return this.bezElszSzlaNev;
  }

  public void setBezElszSzlaNev(final String pBezElszSzlaNev) {
    this.bezElszSzlaNev = pBezElszSzlaNev;
  }

  public String getBezJutSzla() {
    return this.bezJutSzla;
  }

  public void setBezJutSzla(final String pBezJutSzla) {
    this.bezJutSzla = pBezJutSzla;
  }

  public String getBezJutSzlaNev() {
    return this.bezJutSzlaNev;
  }

  public void setBezJutSzlaNev(final String pBezJutSzlaNev) {
    this.bezJutSzlaNev = pBezJutSzlaNev;
  }

  public LocalDate getBezKonyvDat() {
    return this.bezKonyvDat;
  }

  public void setBezKonyvDat(final LocalDate pBezKonyvDat) {
    this.bezKonyvDat = pBezKonyvDat;
  }

  public boolean isBezKonyvelve() {
    return this.bezKonyvelve;
  }

  public void setBezKonyvelve(final boolean pBezKonyvelve) {
    this.bezKonyvelve = pBezKonyvelve;
  }

  public double getBezParDarab() {
    return this.bezParDarab;
  }

  public void setBezParDarab(final double pBezParDarab) {
    this.bezParDarab = pBezParDarab;
  }

  public ZonedDateTime getBezMddat() {
    return this.bezMddat;
  }

  public void setBezMddat(final ZonedDateTime pBezMddat) {
    this.bezMddat = pBezMddat;
  }
}
