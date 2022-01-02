package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Osztalek;

@Document(collection = "osztalek")
public class OsztalekCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Befektetés azonosító. */
  private String        oszBefektetesId;
  /** Osztalék tétel azonosítója. */
  private String        oszAzon;
  /** Osztalék dátuma. */
  private LocalDate     oszDatum;
  /** Befektetés darabszáma. */
  private double        oszBefDarab;
  /** Osztalék mértéke. */
  private double        oszMertek;
  /** Osztalék összege. */
  private double        oszOsszeg;
  /** Adó %-a. */
  private double        oszAdoSzaz;
  /** Adó mértéke. */
  private double        oszAdo;
  /** Adó számla. */
  private String        oszAdoSzamla;
  /** Könyvelve van-e. */
  private boolean       oszKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime oszMddat;

  public OsztalekCol() {
  }

  public OsztalekCol(final Osztalek osztalek) {
    this.id = osztalek.getId();
    this.oszBefektetesId = osztalek.getOszBefektetesId();
    this.oszAzon = osztalek.getOszAzon();
    this.oszDatum = osztalek.getOszDatum();
    this.oszBefDarab = osztalek.getOszBefDarab();
    this.oszMertek = osztalek.getOszMertek();
    this.oszOsszeg = osztalek.getOszOsszeg();
    this.oszAdoSzaz = osztalek.getOszAdoSzaz();
    this.oszAdo = osztalek.getOszAdo();
    this.oszAdoSzamla = osztalek.getOszAdoSzamla();
    this.oszKonyvelve = osztalek.isOszKonyvelve();
    this.oszMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getOszBefektetesId() {
    return this.oszBefektetesId;
  }

  public void setOszBefektetesId(final String pOszBefektetesId) {
    this.oszBefektetesId = pOszBefektetesId;
  }

  public String getOszAzon() {
    return this.oszAzon;
  }

  public void setOszAzon(final String poszAzon) {
    this.oszAzon = poszAzon;
  }

  public LocalDate getOszDatum() {
    return this.oszDatum;
  }

  public void setOszDatum(final LocalDate pOszDatum) {
    this.oszDatum = pOszDatum;
  }

  public double getOszBefDarab() {
    return this.oszBefDarab;
  }

  public void setOszBefDarab(final double pOszBefDarab) {
    this.oszBefDarab = pOszBefDarab;
  }

  public double getOszMertek() {
    return this.oszMertek;
  }

  public void setOszMertek(final double pOszMertek) {
    this.oszMertek = pOszMertek;
  }

  public double getOszOsszeg() {
    return this.oszOsszeg;
  }

  public void setOszOsszeg(final double pOszOsszeg) {
    this.oszOsszeg = pOszOsszeg;
  }

  public double getOszAdoSzaz() {
    return this.oszAdoSzaz;
  }

  public void setOszAdoSzaz(final double pOszAdoSzaz) {
    this.oszAdoSzaz = pOszAdoSzaz;
  }

  public double getOszAdo() {
    return this.oszAdo;
  }

  public void setOszAdo(final double pOszAdo) {
    this.oszAdo = pOszAdo;
  }

  public String getOszAdoSzamla() {
    return this.oszAdoSzamla;
  }

  public void setOszAdoSzamla(final String pOszAdoSzamla) {
    this.oszAdoSzamla = pOszAdoSzamla;
  }

  public boolean isOszKonyvelve() {
    return this.oszKonyvelve;
  }

  public void setOszKonyvelve(final boolean pOszKonyvelve) {
    this.oszKonyvelve = pOszKonyvelve;
  }

  public ZonedDateTime getOszMddat() {
    return this.oszMddat;
  }

  public void setOszMddat(final ZonedDateTime pOszMddat) {
    this.oszMddat = pOszMddat;
  }

  @Override
  public String toString() {
    return "Osztalek["
        + "id=" + this.id
        + ", osz_befektetes_id=" + this.oszBefektetesId
        + ", osz_azon=" + this.oszAzon
        + ", osz_datum=" + this.oszDatum
        + ", osz_bef_darab=" + this.oszBefDarab
        + ", osz_mertek=" + this.oszMertek
        + ", osz_osszeg=" + this.oszOsszeg
        + ", osz_ado_szaz=" + this.oszAdoSzaz
        + ", osz_ado=" + this.oszAdo
        + ", osz_ado_szamla=" + this.oszAdoSzamla
        + ", osz_konyvelve=" + this.oszKonyvelve
        + ", osz_mddat=" + this.oszMddat
        + "]";
  }
}
