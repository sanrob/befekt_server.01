package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Kiadas;

@Document(collection = "kiadasok")
public class KiadasCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Kiadás dátuma. */
  private LocalDate     kiaDatum;
  /** Kiadás éve. */
  private int           kiaEv;
  /** Kiadás azonosítója. */
  private String        kiaAzon;
  /** Kiadás elnevezése. */
  private String        kiaSzoveg;
  /** Kiadás összege. */
  private double        kiaOsszeg;
  /** Kiadási számla azonosítója. */
  private String        kiaSzamla;
  /** Kiadási típusa. */
  private String        kiaTipus;
  /** Számlaforgalomba való könyvelés megtörtént-e. */
  private boolean       kiaSzlaKonyv;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime kiaMddat;

  public KiadasCol() {
  }

  public KiadasCol(final Kiadas kiadas) {
    this.id = kiadas.getId();
    this.kiaDatum = kiadas.getKiaDatum();
    this.kiaEv = this.kiaDatum.getYear();
    this.kiaSzoveg = kiadas.getKiaSzoveg();
    this.kiaOsszeg = kiadas.getKiaOsszeg();
    this.kiaSzamla = kiadas.getKiaSzamla();
    this.kiaTipus = kiadas.getKiaTipus();
    this.kiaMddat = ZonedDateTime.now(ZoneId.systemDefault());

  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public LocalDate getKiaDatum() {
    return this.kiaDatum;
  }

  public void setKiaDatum(final LocalDate pKiaDatum) {
    this.kiaDatum = pKiaDatum;
  }

  public int getKiaEv() {
    return this.kiaEv;
  }

  public void setKiaEv(final int pKiaEv) {
    this.kiaEv = pKiaEv;
  }

  public String getKiaAzon() {
    return this.kiaAzon;
  }

  public void setKiaAzon(final String pKiaAzon) {
    this.kiaAzon = pKiaAzon;
  }

  public String getKiaSzoveg() {
    return this.kiaSzoveg;
  }

  public void setKiaSzoveg(final String pKiaSzoveg) {
    this.kiaSzoveg = pKiaSzoveg;
  }

  public double getKiaOsszeg() {
    return this.kiaOsszeg;
  }

  public void setKiaOsszeg(final double pKiaOsszeg) {
    this.kiaOsszeg = pKiaOsszeg;
  }

  public String getKiaSzamla() {
    return this.kiaSzamla;
  }

  public void setKiaSzamla(final String pKiaSzamla) {
    this.kiaSzamla = pKiaSzamla;
  }

  public String getKiaTipus() {
    return this.kiaTipus;
  }

  public void setKiaTipus(final String pKiaTipus) {
    this.kiaTipus = pKiaTipus;
  }

  public boolean isKiaSzlaKonyv() {
    return this.kiaSzlaKonyv;
  }

  public void setKiaSzlaKonyv(final boolean pKiaSzlaKonyv) {
    this.kiaSzlaKonyv = pKiaSzlaKonyv;
  }

  public ZonedDateTime getKiaMddat() {
    return this.kiaMddat;
  }

  public void setKiaMddat(final ZonedDateTime pKiaMddat) {
    this.kiaMddat = pKiaMddat;
  }

  @Override
  public String toString() {
    return "Kiadas["
        + "id=" + this.id
        + ", kia_datum=" + this.kiaDatum
        + ", kia_ev=" + this.kiaEv
        + ", kia_azon=" + this.kiaAzon
        + ", kia_szoveg=" + this.kiaSzoveg
        + ", kia_osszeg=" + this.kiaOsszeg
        + ", kia_szamla=" + this.kiaSzamla
        + ", kia_tipus=" + this.kiaTipus
        + ", kia_szla_konyv=" + this.kiaSzlaKonyv
        + ", kia_mddat=" + this.kiaMddat
        + "]";
  }
}
