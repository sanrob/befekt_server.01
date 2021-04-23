package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.BefektZaras;

@Document(collection = "befekt_zaras")
public class BefektZarasCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Befektetés azonosítója. */
  private String        bezAzon;
  /** Befektetés éve. */
  private int           bezEv;
  /** Befektetés dátuma. */
  private LocalDate     bezDatum;
  /** Befektetés fajta kódja. */
  private String        bezBffKod;
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
  /** Jutalék számla kódja. */
  private String        bezJutSzla;
  /** Könyvelés dátuma. */
  private LocalDate     bezKonyvDat;
  /** Le van-e már könyvelve. */
  private boolean       bezKonyvelve;
  /** Párosított darabszám. */
  private double        bezParDarab;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime bezMddat;

  public BefektZarasCol() {
  }

  public BefektZarasCol(final BefektZaras befektZaras) {
    this.id = befektZaras.getId();
    this.bezDatum = befektZaras.getBezDatum();
    this.bezEv = this.getBezDatum().getYear();
    this.bezBffKod = befektZaras.getBezBffKod();
    this.bezDarab = befektZaras.getBezDarab();
    this.bezArfolyam = befektZaras.getBezArfolyam();
    this.bezErtek = befektZaras.getBezErtek();
    this.bezJutSzaz = befektZaras.getBezJutSzaz();
    this.bezJutErtek = befektZaras.getBezJutErtek();
    this.bezRealErtek = befektZaras.getBezRealErtek();
    this.bezElszSzla = befektZaras.getBezElszSzla();
    this.bezJutSzla = befektZaras.getBezJutSzla();
    this.bezKonyvDat = befektZaras.getBezKonyvDat();
    this.bezMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  public int getBezEv() {
    return this.bezEv;
  }

  public void setBezEv(final int pBezEv) {
    this.bezEv = pBezEv;
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

  public String getBezJutSzla() {
    return this.bezJutSzla;
  }

  public void setBezJutSzla(final String pBezJutSzla) {
    this.bezJutSzla = pBezJutSzla;
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

  @Override
  public String toString() {
    return "BefektZaras["
        + "id=" + this.id
        + ", bez_azon=" + this.bezAzon
        + ", bez_ev=" + this.bezEv
        + ", bez_datum=" + this.bezDatum
        + ", bez_bff_kod=" + this.bezBffKod
        + ", bez_darab=" + this.bezDarab
        + ", bez_arfolyam=" + this.bezArfolyam
        + ", bez_ertek=" + this.bezErtek
        + ", bez_jut_szaz=" + this.bezJutSzaz
        + ", bez_jut_ertek=" + this.bezJutErtek
        + ", bez_real_ertek=" + this.bezRealErtek
        + ", bez_elsz_szla=" + this.bezElszSzla
        + ", bez_jut_szla=" + this.bezJutSzla
        + ", bez_konyv_dat=" + this.bezKonyvDat
        + ", bez_konyvelve=" + this.bezKonyvelve
        + ", bez_par_darab=" + this.bezParDarab
        + ", bez_mddat=" + this.bezMddat
        + "]";
  }
}
