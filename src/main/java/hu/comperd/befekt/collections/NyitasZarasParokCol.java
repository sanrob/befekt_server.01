package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.NyitasZarasParok;

@Document(collection = "nyitas_zaras_parok")
public class NyitasZarasParokCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Nyitó tétel azonosítója. */
  private String        parNyitAzon;
  /** Záró tétel azonosítója. */
  private String        parZarAzon;
  /** Párosított darabszám. */
  private double        parDarab;
  /** Nyitás dátuma. */
  private LocalDate     parNyitDatum;
  /** Nyitás dátuma. */
  private LocalDate     parZarDatum;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime parMddat;

  public NyitasZarasParokCol() {
  }

  public NyitasZarasParokCol(final NyitasZarasParok nyitasZarasParok) {
    this.id = nyitasZarasParok.getId();
    this.parNyitAzon = nyitasZarasParok.getParNyitAzon();
    this.parZarAzon = nyitasZarasParok.getParZarAzon();
    this.parDarab = nyitasZarasParok.getParDarab();
    this.parNyitDatum = nyitasZarasParok.getParNyitDatum();
    this.parZarDatum = nyitasZarasParok.getParZarDatum();
    this.parMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getParNyitAzon() {
    return this.parNyitAzon;
  }

  public void setParNyitAzon(final String pParNyitAzon) {
    this.parNyitAzon = pParNyitAzon;
  }

  public String getParZarAzon() {
    return this.parZarAzon;
  }

  public void setParZarAzon(final String pParZarAzon) {
    this.parZarAzon = pParZarAzon;
  }

  public double getParDarab() {
    return this.parDarab;
  }

  public void setParDarab(final double pParDarab) {
    this.parDarab = pParDarab;
  }

  public LocalDate getParNyitDatum() {
    return this.parNyitDatum;
  }

  public void setParNyitDatum(final LocalDate pParNyitDatum) {
    this.parNyitDatum = pParNyitDatum;
  }

  public LocalDate getParZarDatum() {
    return this.parZarDatum;
  }

  public void setParZarDatum(final LocalDate pParZarDatum) {
    this.parZarDatum = pParZarDatum;
  }

  public ZonedDateTime getParMddat() {
    return this.parMddat;
  }

  public void setParMddat(final ZonedDateTime pParMddat) {
    this.parMddat = pParMddat;
  }

  @Override
  public String toString() {
    return "NyitasZarasParok["
        + "id=" + this.id
        + ", par_nyit_azon=" + this.parNyitAzon
        + ", par_zar_azon=" + this.parZarAzon
        + ", par_darab=" + this.parDarab
        + ", par_nyit_datum=" + this.parNyitDatum
        + ", par_zar_datum=" + this.parZarDatum
        + ", par_mddat=" + this.parMddat
        + "]";
  }
}
