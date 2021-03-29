package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;

public class Szamlavezeto {
  /** Azonosító. */
  private String        id;
  /** Számlavezető kódja. */
  private String        szvKod;
  /** Számlavezető megnevezése. */
  private String        szvMegnev;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime szvMddat;

  public Szamlavezeto() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getSzvKod() {
    return this.szvKod;
  }

  public void setSzvKod(final String pSzvKod) {
    this.szvKod = pSzvKod;
  }

  public String getSzvMegnev() {
    return this.szvMegnev;
  }

  public void setSzvMegnev(final String pSzvMegnev) {
    this.szvMegnev = pSzvMegnev;
  }

  public ZonedDateTime getSzvMddat() {
    return this.szvMddat;
  }

  public void setSzvMddat(final ZonedDateTime pSzvMddat) {
    this.szvMddat = pSzvMddat;
  }
}
