package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Befektetes {
  /** Azonosító. */
  private String        id;
  /** Befektetés azonosítója. */
  private String        befAzon;
  /** Befektetés dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     befDatum;
  /** Befektetés fajta kódja. */
  private String        befBffKod;
  /** Befektetés fajta megnevezése. */
  private String        befBffKodNev;
  /** Határidős befekteté-e?. */
  private boolean       befBffTip;
  /** Befektetett darabszám. */
  private double        befDarab;
  /** Nyitás árfolyama. */
  private double        befArfolyam;
  /** Nyitás árfolyama. */
  private double        befErtek;
  /** Jutalék %-a. */
  private double        befJutSzaz;
  /** Jutalék értéke. */
  private double        befJutErtek;
  /** Bekerülési érték. */
  private double        befBekerErtek;
  /** Elszámolási számla kódja. */
  private String        befElszSzla;
  /** Elszámolási számla megnevezése. */
  private String        befElszSzlaNev;
  /** Jutalék számla kódja. */
  private String        befJutSzla;
  /** Jutalék számla megnevezése. */
  private String        befJutSzlaNev;
  /** Könyvelés dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     befKonyvDat;
  /** Le van-e már könyvelve. */
  private boolean       befKonyvelve;
  /** Lezárás dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     befLezDat;
  /** Párosított darabszám. */
  private double        befParDarab;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime befMddat;

  public Befektetes() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getBefAzon() {
    return this.befAzon;
  }

  public void setBefAzon(final String pBefAzon) {
    this.befAzon = pBefAzon;
  }

  public LocalDate getBefDatum() {
    return this.befDatum;
  }

  public void setBefDatum(final LocalDate pBefDatum) {
    this.befDatum = pBefDatum;
  }

  public String getBefBffKod() {
    return this.befBffKod;
  }

  public void setBefBffKod(final String pBefBffKod) {
    this.befBffKod = pBefBffKod;
  }

  public String getBefBffKodNev() {
    return this.befBffKodNev;
  }

  public void setBefBffKodNev(final String pBefBffKodNev) {
    this.befBffKodNev = pBefBffKodNev;
  }

  public boolean getBefBffTip() {
    return this.befBffTip;
  }

  public void setBefBffTip(final boolean pBefBffTip) {
    this.befBffTip = pBefBffTip;
  }

  public double getBefDarab() {
    return this.befDarab;
  }

  public void setBefDarab(final double pBefDarab) {
    this.befDarab = pBefDarab;
  }

  public double getBefArfolyam() {
    return this.befArfolyam;
  }

  public void setBefArfolyam(final double pBefArfolyam) {
    this.befArfolyam = pBefArfolyam;
  }

  public double getBefErtek() {
    return this.befErtek;
  }

  public void setBefErtek(final double pBefErtek) {
    this.befErtek = pBefErtek;
  }

  public double getBefJutSzaz() {
    return this.befJutSzaz;
  }

  public void setBefJutSzaz(final double pBefJutSzaz) {
    this.befJutSzaz = pBefJutSzaz;
  }

  public double getBefJutErtek() {
    return this.befJutErtek;
  }

  public void setBefJutErtek(final double pBefJutErtek) {
    this.befJutErtek = pBefJutErtek;
  }

  public double getBefBekerErtek() {
    return this.befBekerErtek;
  }

  public void setBefBekerErtek(final double pBefBekerErtek) {
    this.befBekerErtek = pBefBekerErtek;
  }

  public String getBefElszSzla() {
    return this.befElszSzla;
  }

  public void setBefElszSzla(final String pBefElszSzla) {
    this.befElszSzla = pBefElszSzla;
  }

  public String getBefElszSzlaNev() {
    return this.befElszSzlaNev;
  }

  public void setBefElszSzlaNev(final String pBefElszSzlaNev) {
    this.befElszSzlaNev = pBefElszSzlaNev;
  }

  public String getBefJutSzla() {
    return this.befJutSzla;
  }

  public void setBefJutSzla(final String pBefJutSzla) {
    this.befJutSzla = pBefJutSzla;
  }

  public String getBefJutSzlaNev() {
    return this.befJutSzlaNev;
  }

  public void setBefJutSzlaNev(final String pBefJutSzlaNev) {
    this.befJutSzlaNev = pBefJutSzlaNev;
  }

  public LocalDate getBefKonyvDat() {
    return this.befKonyvDat;
  }

  public void setBefKonyvDat(final LocalDate pBefKonyvDat) {
    this.befKonyvDat = pBefKonyvDat;
  }

  public boolean isBefKonyvelve() {
    return this.befKonyvelve;
  }

  public void setBefKonyvelve(final boolean pBefKonyvelve) {
    this.befKonyvelve = pBefKonyvelve;
  }

  public LocalDate getBefLezDat() {
    return this.befLezDat;
  }

  public void setBefLezDat(final LocalDate pBefLezDat) {
    this.befLezDat = pBefLezDat;
  }

  public double getBefParDarab() {
    return this.befParDarab;
  }

  public void setBefParDarab(final double pBefParDarab) {
    this.befParDarab = pBefParDarab;
  }

  public ZonedDateTime getBefMddat() {
    return this.befMddat;
  }

  public void setBefMddat(final ZonedDateTime pBefMddat) {
    this.befMddat = pBefMddat;
  }
}
