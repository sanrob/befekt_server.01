package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;
import java.util.Map;
import hu.comperd.befekt.collections.HavKiadTervCol;
import hu.comperd.befekt.repositories.SzamlaRepository;

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
  private String        hktDatumTol;
  /** Tervezet érvényesség vége. */
  private String        hktDatumIg;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hktMddat;

  public HavKiadTerv() {
    //
  }

  public HavKiadTerv(final HavKiadTervCol havKiadTervCol) {
    this.id = havKiadTervCol.getId();
    this.hktSorszam = havKiadTervCol.getHktSorszam();
    this.hktMegnev = havKiadTervCol.getHktMegnev();
    this.hktSzamla = havKiadTervCol.getHktSzamla();
    this.hktOsszeg = havKiadTervCol.getHktOsszeg();
    this.hktDatumTol = havKiadTervCol.getHktDatumTol();
    this.hktDatumIg = havKiadTervCol.getHktDatumIg();
    this.hktMddat = havKiadTervCol.getHktMddat();
  }

  public HavKiadTerv(final Map.Entry<String, Double> tetel) {
    this.hktOsszeg = tetel.getValue();
  }

  public HavKiadTerv setDatas(final SzamlaRepository szlarepo) {
    this.hktSzamlaNev = szlarepo.findBySzaKod(this.hktSzamla).getSzaMegnev();
    return this;
  }

  public HavKiadTerv setDatasOsszesen() {
    this.hktSorszam = "000";
    this.hktMegnev = "Összesen";
    return this;
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

  public String getHktDatumTol() {
    return this.hktDatumTol;
  }

  public void setHktDatumTol(final String pHktDatumTol) {
    this.hktDatumTol = pHktDatumTol;
  }

  public String getHktDatumIg() {
    return this.hktDatumIg;
  }

  public void setHktDatumIg(final String pHktDatumIg) {
    this.hktDatumIg = pHktDatumIg;
  }

  public ZonedDateTime getHktMddat() {
    return this.hktMddat;
  }

  public void setHktMddat(final ZonedDateTime pHktMddat) {
    this.hktMddat = pHktMddat;
  }
}
