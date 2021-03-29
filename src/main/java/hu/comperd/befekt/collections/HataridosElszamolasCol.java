package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.HataridosElszamolas;

@Document(collection = "hataridos_elszamolas")
public class HataridosElszamolasCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Határidős elszámolás tétel azonosítója. */
  private String        hatAzon;
  /** Határidős nyitás ID. */
  private String        hatNyitoId;
  /** Határidős nyitás azonosítója. */
  private String        hatNyitoAzon;
  /** Határidő típusa: vétel/eladás. */
  private String        hatTipus;
  /** Elszámolás dátuma. */
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
  /** Határidős elszámoló számla. */
  private String        hatElszSzamla;
  /** Könyvelve van-e. */
  private boolean       hatKonyvelve;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hatMddat;

  public HataridosElszamolasCol() {
  }

  public HataridosElszamolasCol(final HataridosElszamolas hataridosElszamolas) {
    this.id = hataridosElszamolas.getId();
    this.hatAzon = hataridosElszamolas.getHatAzon();
    this.hatNyitoId = hataridosElszamolas.getHatNyitoId();
    this.hatNyitoAzon = hataridosElszamolas.getHatNyitoAzon();
    this.hatTipus = hataridosElszamolas.getHatTipus();
    this.hatElszDatum = hataridosElszamolas.getHatElszDatum();
    this.hatElozoArf = hataridosElszamolas.getHatElozoArf();
    this.hatElszArf = hataridosElszamolas.getHatElszArf();
    this.hatDarab = hataridosElszamolas.getHatDarab();
    this.hatElszOsszeg = hataridosElszamolas.getHatElszOsszeg();
    this.hatHatSzamla = hataridosElszamolas.getHatHatSzamla();
    this.hatElszSzamla = hataridosElszamolas.getHatElszSzamla();
    this.hatKonyvelve = hataridosElszamolas.isHatKonyvelve();
    this.hatMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  public String getHatElszSzamla() {
    return this.hatElszSzamla;
  }

  public void setHatElszSzamla(final String pHatElszSzamla) {
    this.hatElszSzamla = pHatElszSzamla;
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

  @Override
  public String toString() {
    return "HataridosElszamolas["
        + "id=" + this.id
        + ", hat_azon=" + this.hatAzon
        + ", hat_nyito_id=" + this.hatNyitoId
        + ", hat_nyito_azon=" + this.hatNyitoAzon
        + ", hat_tipus=" + this.hatTipus
        + ", hat_elsz_datum=" + this.hatElszDatum
        + ", hat_elozo_arf=" + this.hatElozoArf
        + ", hat_elsz_arf=" + this.hatElszArf
        + ", hat_darab=" + this.hatDarab
        + ", hat_elsz_osszeg=" + this.hatElszOsszeg
        + ", hat_hat_szamla=" + this.hatHatSzamla
        + ", hat_elsz_szamla=" + this.hatElszSzamla
        + ", hat_konyvelve=" + this.isHatKonyvelve()
        + ", hat_mddat=" + this.hatMddat
        + "]";
  }
}
