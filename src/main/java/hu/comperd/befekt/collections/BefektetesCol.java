package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Befektetes;

@Document(collection = "befektetesek")
public class BefektetesCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Befektetés azonosítója. */
  private String        befAzon;
  /** Befektetés éve. */
  private int           befEv;
  /** Befektetés dátuma. */
  private LocalDate     befDatum;
  /** Befektetés fajta kódja. */
  private String        befBffKod;
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
  /** Könyvelés dátuma. */
  private LocalDate     befKonyvDat;
  /** Le van-e már könyvelve. */
  private boolean       befKonyvelve;
  /** Teljes lezárás dátuma. */
  private LocalDate     befLezDat;
  /** Párosított darabszám. */
  private double        befParDarab;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime befMddat;

  public BefektetesCol() {
  }

  public BefektetesCol(final Befektetes befektetes) {
    this.id = befektetes.getId();
    this.befDatum = befektetes.getBefDatum();
    this.befEv = this.getBefDatum().getYear();
    this.befBffKod = befektetes.getBefBffKod();
    this.befDarab = befektetes.getBefDarab();
    this.befArfolyam = befektetes.getBefArfolyam();
    this.befErtek = befektetes.getBefErtek();
    this.befJutSzaz = befektetes.getBefJutSzaz();
    this.befJutErtek = befektetes.getBefJutErtek();
    this.befBekerErtek = befektetes.getBefBekerErtek();
    this.befKonyvDat = befektetes.getBefKonyvDat();
    this.befMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  public int getBefEv() {
    return this.befEv;
  }

  public void setBefEv(final int pBefEv) {
    this.befEv = pBefEv;
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

  @Override
  public String toString() {
    return "Befektetes["
        + "id=" + this.id
        + ", bef_azon=" + this.befAzon
        + ", bef_ev=" + this.befEv
        + ", bef_datum=" + this.befDatum
        + ", bef_bff_kod=" + this.befBffKod
        + ", bef_darab=" + this.befDarab
        + ", bef_arfolyam=" + this.befArfolyam
        + ", bef_ertek=" + this.befErtek
        + ", bef_jut_szaz=" + this.befJutSzaz
        + ", bef_jut_ertek=" + this.befJutErtek
        + ", bef_beker_ertek=" + this.befBekerErtek
        + ", bef_konyv_dat=" + this.befKonyvDat
        + ", bef_konyvelve=" + this.befKonyvelve
        + ", bef_lez_dat=" + this.befLezDat
        + ", bef_par_darab=" + this.befParDarab
        + ", bef_mddat=" + this.befMddat
        + "]";
  }
}
