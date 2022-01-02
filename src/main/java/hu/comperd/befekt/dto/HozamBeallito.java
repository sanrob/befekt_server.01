package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HozamBeallito {
  /** Azonosító. */
  private String        id;
  /** Párosítás azonosító. */
  private String        hobParositasId;
  /** Hozam beállító tétel azonosítója. */
  private String        hobAzon;
  /** Záró tétel dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
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
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     hobAdoDatum;
  /** Adó %-a. */
  private double        hobAdoSzaz;
  /** Adó mértéke. */
  private double        hobAdo;
  /** Tartozik számla. */
  private String        hobTartSzamla;
  /** Tartozik számla neve. */
  private String        hobTartSzamlaNev;
  /** Követel számla. */
  private String        hobKovSzamla;
  /** Követel számla neve. */
  private String        hobKovSzamlaNev;
  /** Könyvelve van-e. */
  private boolean       hobKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hobMddat;

  public HozamBeallito() {
    //
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

  public String getHobTartSzamlaNev() {
    return this.hobTartSzamlaNev;
  }

  public void setHobTartSzamlaNev(final String pHobTartSzamlaNev) {
    this.hobTartSzamlaNev = pHobTartSzamlaNev;
  }

  public String getHobKovSzamla() {
    return this.hobKovSzamla;
  }

  public void setHobKovSzamla(final String pHobKovSzamla) {
    this.hobKovSzamla = pHobKovSzamla;
  }

  public String getHobKovSzamlaNev() {
    return this.hobKovSzamlaNev;
  }

  public void setHobKovSzamlaNev(final String pHobKovSzamlaNev) {
    this.hobKovSzamlaNev = pHobKovSzamlaNev;
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
}
