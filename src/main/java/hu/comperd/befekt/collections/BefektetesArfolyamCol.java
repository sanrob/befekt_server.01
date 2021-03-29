package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.BefektetesArfolyam;

@Document(collection = "befektetes_arfolyam")
public class BefektetesArfolyamCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Befektetés azonosítója. */
  private String        beaBefAzon;
  /** Árfolyam dátuma. */
  private LocalDate     beaArfDatum;
  /** Napi árfolyama. */
  private double        beaArfolyam;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime beaMddat;

  public BefektetesArfolyamCol() {
  }

  public BefektetesArfolyamCol(final BefektetesArfolyam befektetesArfolyam) {
    this.id = befektetesArfolyam.getId();
    this.beaBefAzon = befektetesArfolyam.getBeaBefAzon();
    this.beaArfDatum = befektetesArfolyam.getBeaArfDatum();
    this.beaArfolyam = befektetesArfolyam.getBeaArfolyam();
    this.beaMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getBeaBefAzon() {
    return this.beaBefAzon;
  }

  public void setBeaBefAzon(final String pBeaBefAzon) {
    this.beaBefAzon = pBeaBefAzon;
  }

  public LocalDate getBeaArfDatum() {
    return this.beaArfDatum;
  }

  public void setBeaArfDatum(final LocalDate pBeaArfDatum) {
    this.beaArfDatum = pBeaArfDatum;
  }

  public double getBeaArfolyam() {
    return this.beaArfolyam;
  }

  public void setBeaArfolyam(final double pBeaArfolyam) {
    this.beaArfolyam = pBeaArfolyam;
  }

  public ZonedDateTime getBeaMddat() {
    return this.beaMddat;
  }

  public void setBeaMddat(final ZonedDateTime pBeaMddat) {
    this.beaMddat = pBeaMddat;
  }

  @Override
  public String toString() {
    return "BefektetesArfolyam["
        + "id=" + this.id
        + ", bea_bef_azon=" + this.beaBefAzon
        + ", bea_arf_datum=" + this.beaArfDatum
        + ", bea_arfolyam=" + this.beaArfolyam
        + ", bea_mddat=" + this.beaMddat
        + "]";
  }
}
