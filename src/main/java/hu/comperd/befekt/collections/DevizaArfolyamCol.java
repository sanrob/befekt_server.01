package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.DevizaArfolyam;

@Document(collection = "deviza_arfolyam")
public class DevizaArfolyamCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Miről deviza kódja. */
  private String        deaMirolDevKod;
  /** Mire deviza kódja. */
  private String        deaMireDevKod;
  /** Árfolyam dátuma. */
  private LocalDate     deaArfDatum;
  /** Napi árfolyama. */
  private double        deaArfolyam;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime deaMddat;

  public DevizaArfolyamCol() {
  }

  public DevizaArfolyamCol(final DevizaArfolyam devizaArfolyam) {
    this.id = devizaArfolyam.getId();
    this.deaMirolDevKod = devizaArfolyam.getDeaMirolDevKod();
    this.deaMireDevKod = devizaArfolyam.getDeaMireDevKod();
    this.deaArfDatum = devizaArfolyam.getDeaArfDatum();
    this.deaArfolyam = devizaArfolyam.getDeaArfolyam();
    this.deaMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getDeaMirolDevKod() {
    return this.deaMirolDevKod;
  }

  public void setDeaMirolDevKod(final String pDeaMirolDevKod) {
    this.deaMirolDevKod = pDeaMirolDevKod;
  }

  public String getDeaMireDevKod() {
    return this.deaMireDevKod;
  }

  public void setDeaMireDevKod(final String pDeaMireDevKod) {
    this.deaMireDevKod = pDeaMireDevKod;
  }

  public LocalDate getDeaArfDatum() {
    return this.deaArfDatum;
  }

  public void setDeaArfDatum(final LocalDate pDeaArfDatum) {
    this.deaArfDatum = pDeaArfDatum;
  }

  public double getDeaArfolyam() {
    return this.deaArfolyam;
  }

  public void setDeaArfolyam(final double pDeaArfolyam) {
    this.deaArfolyam = pDeaArfolyam;
  }

  public ZonedDateTime getDeaMddat() {
    return this.deaMddat;
  }

  public void setDeaMddat(final ZonedDateTime pDeaMddat) {
    this.deaMddat = pDeaMddat;
  }

  @Override
  public String toString() {
    return "DevizaArfolyam["
        + "id=" + this.id
        + ", dea_mirol_dev_kod=" + this.deaMirolDevKod
        + ", dea_mire_dev_kod=" + this.deaMireDevKod
        + ", dea_arf_datum=" + this.deaArfDatum
        + ", dea_arfolyam=" + this.deaArfolyam
        + ", dea_mddat=" + this.deaMddat
        + "]";
  }
}
