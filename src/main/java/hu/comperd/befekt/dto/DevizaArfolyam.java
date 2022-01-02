package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import hu.comperd.befekt.collections.DevizaArfolyamCol;

public class DevizaArfolyam {
  /** Azonosító. */
  private String        id;
  /** Miről deviza kódja. */
  private String        deaMirolDevKod;
  /** Mire deviza kódja. */
  private String        deaMireDevKod;
  /** Árfolyam dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     deaArfDatum;
  /** Napi árfolyama. */
  private double        deaArfolyam;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime deaMddat;

  public DevizaArfolyam() {
    //
  }

  public DevizaArfolyam(final DevizaArfolyamCol devizaArfolyamCol) {
    this.id = devizaArfolyamCol.getId();
    this.deaMirolDevKod = devizaArfolyamCol.getDeaMirolDevKod();
    this.deaMireDevKod = devizaArfolyamCol.getDeaMireDevKod();
    this.deaArfDatum = devizaArfolyamCol.getDeaArfDatum();
    this.deaArfolyam = devizaArfolyamCol.getDeaArfolyam();
    this.deaMddat = devizaArfolyamCol.getDeaMddat();
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
}
