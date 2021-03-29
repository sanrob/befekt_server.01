package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BefektetesArfolyam {
  /** Azonosító. */
  private String        id;
  /** Befektetés azonosítója. */
  private String        beaBefAzon;
  /** Árfolyam dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     beaArfDatum;
  /** Napi árfolyama. */
  private double        beaArfolyam;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime beaMddat;

  public BefektetesArfolyam() {
    //
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
}
