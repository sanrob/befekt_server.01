package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HavKiadTerv {
  /** Azonosító. */
  private String        id;
  /** Tervezet sorszáma. */
  private String        hktSorszam;
  /** Tervezet megnevezése. */
  private String        hktMegnev;
  /** Tervezethez tartozó számla azonosítója. */
  private String        hktSzamla;
  /** Tervezethez tartozó számla megbevezése. */
  private String        hktSzamlaNev;
  /** Tervezett összeg. */
  private double        hktOsszeg;
  /** Tervezet érvényesség kezdete. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     hktDatumTol;
  /** Tervezet érvényesség vége. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     hktDatumIg;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hktMddat;

  public HavKiadTerv() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getHktSorszam() {
    return this.hktSorszam;
  }

  public void setHktSorszam(final String pHktSorszam) {
    this.hktSorszam = pHktSorszam;
  }

  public String getHktMegnev() {
    return this.hktMegnev;
  }

  public void setHktMegnev(final String pHktMegnev) {
    this.hktMegnev = pHktMegnev;
  }

  public String getHktSzamla() {
    return this.hktSzamla;
  }

  public void setHktSzamla(final String pHktSzamla) {
    this.hktSzamla = pHktSzamla;
  }

  public String getHktSzamlaNev() {
    return this.hktSzamlaNev;
  }

  public void setHktSzamlaNev(final String pHktSzamlaNev) {
    this.hktSzamlaNev = pHktSzamlaNev;
  }

  public double getHktOsszeg() {
    return this.hktOsszeg;
  }

  public void setHktOsszeg(final double pHktOsszeg) {
    this.hktOsszeg = pHktOsszeg;
  }

  public LocalDate getHktDatumTol() {
    return this.hktDatumTol;
  }

  public void setHktDatumTol(final LocalDate pHktDatumTol) {
    this.hktDatumTol = pHktDatumTol;
  }

  public LocalDate getHktDatumIg() {
    return this.hktDatumIg;
  }

  public void setHktDatumIg(final LocalDate pHktDatumIg) {
    this.hktDatumIg = pHktDatumIg;
  }

  public ZonedDateTime getHktMddat() {
    return this.hktMddat;
  }

  public void setHktMddat(final ZonedDateTime pHktMddat) {
    this.hktMddat = pHktMddat;
  }
}
