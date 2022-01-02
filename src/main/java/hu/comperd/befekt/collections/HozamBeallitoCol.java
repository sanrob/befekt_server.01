package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.HozamBeallito;

@Document(collection = "hozam_beallito")
public class HozamBeallitoCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Párisítás azonosító. */
  private String        hobParositasId;
  /** Hozam beállító tétel azonosítója. */
  private String        hobAzon;
  /** Hozam beállító tétel éve. */
  private int           hobEv;
  /** Záró tétel dátuma. */
  private LocalDate     hobZarDatum;
  /** Bruttó hozam. */
  private double        hobBruttoHozam;
  /** Nyitó jutalék. */
  private double        hobNyitoJutalek;
  /** Záró jutalék. */
  private double        hobZaroJutalek;
  /** Nettó hozam. */
  private double        hobNettoHozam;
  /** Adó könyvelés dátuma. */
  private LocalDate     hobAdoDatum;
  /** Adó %-a. */
  private double        hobAdoSzaz;
  /** Adó mértéke. */
  private double        hobAdo;
  /** Tartozik számla. */
  private String        hobTartSzamla;
  /** Követel számla. */
  private String        hobKovSzamla;
  /** Könyvelve van-e. */
  private boolean       hobKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hobMddat;

  public HozamBeallitoCol() {
  }

  public HozamBeallitoCol(final HozamBeallito hozamBeallito) {
    this.id = hozamBeallito.getId();
    this.hobParositasId = hozamBeallito.getHobParositasId();
    this.hobAzon = hozamBeallito.getHobAzon();
    this.hobEv = hozamBeallito.getHobZarDatum().getYear();
    this.hobZarDatum = hozamBeallito.getHobZarDatum();
    this.hobBruttoHozam = hozamBeallito.getHobBruttoHozam();
    this.hobNyitoJutalek = hozamBeallito.getHobNyitoJutalek();
    this.hobZaroJutalek = hozamBeallito.getHobZaroJutalek();
    this.hobNettoHozam = hozamBeallito.getHobNettoHozam();
    this.hobAdoDatum = hozamBeallito.getHobAdoDatum();
    this.hobAdoSzaz = hozamBeallito.getHobAdoSzaz();
    this.hobAdo = hozamBeallito.getHobAdo();
    this.hobTartSzamla = hozamBeallito.getHobTartSzamla();
    this.hobKovSzamla = hozamBeallito.getHobKovSzamla();
    this.hobKonyvelve = hozamBeallito.isHobKonyvelve();
    this.hobMddat = hozamBeallito.getHobMddat();
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getHobParositasId() {
    return this.hobParositasId;
  }

  public void setHobParositasId(final String pHobParositasId) {
    this.hobParositasId = pHobParositasId;
  }

  public String getHobAzon() {
    return this.hobAzon;
  }

  public void setHobAzon(final String pHobAzon) {
    this.hobAzon = pHobAzon;
  }

  public int getHobEv() {
    return this.hobEv;
  }

  public void setHobEv(final int pHobEv) {
    this.hobEv = pHobEv;
  }

  public LocalDate getHobZarDatum() {
    return this.hobZarDatum;
  }

  public void setHobZarDatum(final LocalDate pHobZarDatum) {
    this.hobZarDatum = pHobZarDatum;
  }

  public double getHobBruttoHozam() {
    return this.hobBruttoHozam;
  }

  public void setHobBruttoHozam(final double pHobBruttoHozam) {
    this.hobBruttoHozam = pHobBruttoHozam;
  }

  public double getHobNyitoJutalek() {
    return this.hobNyitoJutalek;
  }

  public void setHobNyitoJutalek(final double pHobNyitoJutalek) {
    this.hobNyitoJutalek = pHobNyitoJutalek;
  }

  public double getHobZaroJutalek() {
    return this.hobZaroJutalek;
  }

  public void setHobZaroJutalek(final double pHobZaroJutalek) {
    this.hobZaroJutalek = pHobZaroJutalek;
  }

  public double getHobNettoHozam() {
    return this.hobNettoHozam;
  }

  public void setHobNettoHozam(final double pHobNettoHozam) {
    this.hobNettoHozam = pHobNettoHozam;
  }

  public LocalDate getHobAdoDatum() {
    return this.hobAdoDatum;
  }

  public void setHobAdoDatum(final LocalDate pHobAdoDatum) {
    this.hobAdoDatum = pHobAdoDatum;
  }

  public double getHobAdoSzaz() {
    return this.hobAdoSzaz;
  }

  public void setHobAdoSzaz(final double pHobAdoSzaz) {
    this.hobAdoSzaz = pHobAdoSzaz;
  }

  public double getHobAdo() {
    return this.hobAdo;
  }

  public void setHobAdo(final double pHobAdo) {
    this.hobAdo = pHobAdo;
  }

  public String getHobTartSzamla() {
    return this.hobTartSzamla;
  }

  public void setHobTartSzamla(final String pHobTartSzamla) {
    this.hobTartSzamla = pHobTartSzamla;
  }

  public String getHobKovSzamla() {
    return this.hobKovSzamla;
  }

  public void setHobKovSzamla(final String pHobKovSzamla) {
    this.hobKovSzamla = pHobKovSzamla;
  }

  public boolean isHobKonyvelve() {
    return this.hobKonyvelve;
  }

  public void setHobKonyvelve(final boolean pHobKonyvelve) {
    this.hobKonyvelve = pHobKonyvelve;
  }

  public ZonedDateTime getHobMddat() {
    return this.hobMddat;
  }

  public void setHobMddat(final ZonedDateTime pHobMddat) {
    this.hobMddat = pHobMddat;
  }

  @Override
  public String toString() {
    return "HozamBeallito["
        + "id=" + this.id
        + ", hob_parositas_id=" + this.hobParositasId
        + ", hob_azon=" + this.hobAzon
        + ", hob_ev=" + this.hobEv
        + ", hob_zar_datum=" + this.hobZarDatum
        + ", hob_brutto_hozam=" + this.hobBruttoHozam
        + ", hob_nyito_jutalek=" + this.hobNyitoJutalek
        + ", hob_zaro_jutalek=" + this.hobZaroJutalek
        + ", hob_netto_hozam=" + this.hobNettoHozam
        + ", hob_ado_datum=" + this.hobAdoDatum
        + ", hob_ado_szaz=" + this.hobAdoSzaz
        + ", hob_ado=" + this.hobAdo
        + ", hob_tart_szamla=" + this.hobTartSzamla
        + ", hob_kov_szamla=" + this.hobKovSzamla
        + ", hob_konyvelve=" + this.hobKonyvelve
        + ", hob_mddat=" + this.hobMddat
        + "]";
  }
}
