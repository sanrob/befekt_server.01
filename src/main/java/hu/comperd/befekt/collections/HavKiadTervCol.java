package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import hu.comperd.befekt.dto.HavKiadTerv;

@Document(collection = "havi_kiadas_tervezet")
public class HavKiadTervCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Tervezet sorszáma. */
  private String        hktSorszam;
  /** Tervezet megnevezése. */
  private String        hktMegnev;
  /** Tervezethez tartozó számla azonosítója. */
  private String        hktSzamla;
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

  public HavKiadTervCol() {
  }

  public HavKiadTervCol(final HavKiadTerv havKiadTerv) {
    this.id = havKiadTerv.getId();
    this.hktSorszam = havKiadTerv.getHktSorszam();
    this.hktMegnev = havKiadTerv.getHktMegnev();
    this.hktSzamla = havKiadTerv.getHktSzamla();
    this.hktOsszeg = havKiadTerv.getHktOsszeg();
    this.hktDatumTol = havKiadTerv.getHktDatumTol();
    this.hktDatumIg = havKiadTerv.getHktDatumIg();
    this.hktMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  @Override
  public String toString() {
    return "HavKiadTerv["
        + "id=" + this.id
        + ", hkt_sorszam=" + this.hktSorszam
        + ", hkt_megnev=" + this.hktMegnev
        + ", hkt_szamla=" + this.hktSzamla
        + ", hkt_osszeg=" + this.hktOsszeg
        + ", hkt_datumTol=" + this.hktDatumTol
        + ", hkt_datumIg=" + this.hktDatumIg
        + ", hkt_mddat=" + this.hktMddat
        + "]";
  }
}
