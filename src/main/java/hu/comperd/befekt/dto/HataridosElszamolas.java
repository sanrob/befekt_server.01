package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HataridosElszamolas {
  /** Azonosító. */
  private String        id;
  /** Határidős elszámolás tétel azonosítója. */
  private String        hatAzon;
  /** Határidős nyitás ID. */
  private String        hatNyitoId;
  /** Határidős nyitás azonosítója. */
  private String        hatNyitoAzon;
  /** Határidő típusa: vétel/eladás. */
  private String        hatTipus;
  /** Határidő típusának megnevezése. */
  private String        hatTipusNev;
  /** Elszámolás dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     hatElszDatum;
  /** Előző elszámoló árfolyam. */
  private double        hatElozoArf;
  /** Aktuális elszámoló árfolyam. */
  private double        hatElszArf;
  /** Határidő darabszám. */
  private double        hatDarab;
  /** Elszámolt összeg. */
  private double        hatElszOsszeg;
  /** Határidő számla. */
  private String        hatHatSzamla;
  /** Határidős számla neve. */
  private String        hatHatSzamlaNev;
  /** Határidős elszámoló számla. */
  private String        hatElszSzamla;
  /** Határidős elszámoló számla neve. */
  private String        hatElszSzamlaNev;
  /** Migráció utáni tétel mentés megtörtént-e. */
  private boolean       hatMentve;
  /** Könyvelve van-e. */
  private boolean       hatKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hatMddat;

  public HataridosElszamolas() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getHatAzon() {
    return this.hatAzon;
  }

  public void setHatAzon(final String pHatAzon) {
    this.hatAzon = pHatAzon;
  }

  public String getHatNyitoId() {
    return this.hatNyitoId;
  }

  public void setHatNyitoId(final String pHatNyitoId) {
    this.hatNyitoId = pHatNyitoId;
  }

  public String getHatNyitoAzon() {
    return this.hatNyitoAzon;
  }

  public void setHatNyitoAzon(final String pHatNyitoAzon) {
    this.hatNyitoAzon = pHatNyitoAzon;
  }

  public String getHatTipus() {
    return this.hatTipus;
  }

  public void setHatTipus(final String pHatTipus) {
    this.hatTipus = pHatTipus;
  }

  public String getHatTipusNev() {
    return this.hatTipusNev;
  }

  public void setHatTipusNev(final String pHatTipusNev) {
    this.hatTipusNev = pHatTipusNev;
  }

  public LocalDate getHatElszDatum() {
    return this.hatElszDatum;
  }

  public void setHatElszDatum(final LocalDate pHatElszDatum) {
    this.hatElszDatum = pHatElszDatum;
  }

  public double getHatElozoArf() {
    return this.hatElozoArf;
  }

  public void setHatElozoArf(final double pHatElozoArf) {
    this.hatElozoArf = pHatElozoArf;
  }

  public double getHatElszArf() {
    return this.hatElszArf;
  }

  public void setHatElszArf(final double pHatElszArf) {
    this.hatElszArf = pHatElszArf;
  }

  public double getHatDarab() {
    return this.hatDarab;
  }

  public void setHatDarab(final double pHatDarab) {
    this.hatDarab = pHatDarab;
  }

  public double getHatElszOsszeg() {
    return this.hatElszOsszeg;
  }

  public void setHatElszOsszeg(final double pHatElszOsszeg) {
    this.hatElszOsszeg = pHatElszOsszeg;
  }

  public String getHatHatSzamla() {
    return this.hatHatSzamla;
  }

  public void setHatHatSzamla(final String pHatHatSzamla) {
    this.hatHatSzamla = pHatHatSzamla;
  }

  public String getHatHatSzamlaNev() {
    return this.hatHatSzamlaNev;
  }

  public void setHatHatSzamlaNev(final String pHatHatSzamlaNev) {
    this.hatHatSzamlaNev = pHatHatSzamlaNev;
  }

  public String getHatElszSzamla() {
    return this.hatElszSzamla;
  }

  public void setHatElszSzamla(final String pHatElszSzamla) {
    this.hatElszSzamla = pHatElszSzamla;
  }

  public String getHatElszSzamlaNev() {
    return this.hatElszSzamlaNev;
  }

  public void setHatElszSzamlaNev(final String pHatElszSzamlaNev) {
    this.hatElszSzamlaNev = pHatElszSzamlaNev;
  }

  public boolean isHatMentve() {
    return this.hatMentve;
  }

  public void setHatMentve(final boolean pHatMentve) {
    this.hatMentve = pHatMentve;
  }

  public boolean isHatKonyvelve() {
    return this.hatKonyvelve;
  }

  public void setHatKonyvelve(final boolean pHatKonyvelve) {
    this.hatKonyvelve = pHatKonyvelve;
  }

  public ZonedDateTime getHatMddat() {
    return this.hatMddat;
  }

  public void setHatMddat(final ZonedDateTime pHatMddat) {
    this.hatMddat = pHatMddat;
  }
}
