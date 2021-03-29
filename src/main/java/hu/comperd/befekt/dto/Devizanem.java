package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;

public class Devizanem {
  /** Azonosító. */
  private String        id;
  /** Devizanem kódja. */
  private String        devKod;
  /** Devizanem megnevezése. */
  private String        devMegnev;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime devMddat;

  public Devizanem() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getDevKod() {
    return this.devKod;
  }

  public void setDevKod(final String pDevKod) {
    this.devKod = pDevKod;
  }

  public String getDevMegnev() {
    return this.devMegnev;
  }

  public void setDevMegnev(final String pDevMegnev) {
    this.devMegnev = pDevMegnev;
  }

  public ZonedDateTime getDevMddat() {
    return this.devMddat;
  }

  public void setDevMddat(final ZonedDateTime pDevMddat) {
    this.devMddat = pDevMddat;
  }
}
