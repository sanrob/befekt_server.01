package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Kamat;

@Document(collection = "kamat")
public class KamatCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Befektetés azonosító. */
  private String        kamBefektetesId;
  /** Kamat tétel azonosítója. */
  private String        kamAzon;
  /** Kamat dátuma. */
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
  /** Könyvelve van-e. */
  private boolean       kamKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime kamMddat;

  public KamatCol() {
  }

  public KamatCol(final Kamat kamat) {
    this.id = kamat.getId();
    this.kamBefektetesId = kamat.getKamBefektetesId();
    this.kamAzon = kamat.getKamAzon();
    this.kamDatum = kamat.getKamDatum();
    this.kamBefOsszeg = kamat.getKamBefOsszeg();
    this.kamMertek = kamat.getKamMertek();
    this.kamOsszeg = kamat.getKamOsszeg();
    this.kamAdoSzaz = kamat.getKamAdoSzaz();
    this.kamAdo = kamat.getKamAdo();
    this.kamAdoSzamla = kamat.getKamAdoSzamla();
    this.kamKonyvelve = kamat.isKamKonyvelve();
    this.kamMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  @Override
  public String toString() {
    return "Kamat["
        + "id=" + this.id
        + ", kam_befektetes_id=" + this.kamBefektetesId
        + ", kam_azon=" + this.kamAzon
        + ", kam_datum=" + this.kamDatum
        + ", kam_bef_osszeg=" + this.kamBefOsszeg
        + ", kam_mertek=" + this.kamMertek
        + ", kam_osszeg=" + this.kamOsszeg
        + ", kam_ado_szaz=" + this.kamAdoSzaz
        + ", kam_ado=" + this.kamAdo
        + ", kam_ado_szamla=" + this.kamAdoSzamla
        + ", kam_konyvelve=" + this.kamKonyvelve
        + ", kam_mddat=" + this.kamMddat
        + "]";
  }
}
