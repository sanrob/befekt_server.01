package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;

public class Szamla {
  /** Azonosító. */
  private String        id;
  /** Számla kódja. */
  private String        szaKod;
  /** Számla megnevezése. */
  private String        szaMegnev;
  /** Számla típusa. */
  private String        szaSzlatip;
  /** Számla típus megnevezése. */
  private String        szaSzlatipNev;
  /** Számlát kezelő pénzintézet. */
  private String        szaPenzint;
  /** Számlát kezelő pénzintézet megnevezése. */
  private String        szaPenzintNev;
  /** Számla devizaneme. */
  private String        szaDeviza;
  /** Számla devizanem megnevezése. */
  private String        szaDevizaNev;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime szaMddat;

  public Szamla() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getSzaKod() {
    return this.szaKod;
  }

  public void setSzaKod(final String pSzaKod) {
    this.szaKod = pSzaKod;
  }

  public String getSzaMegnev() {
    return this.szaMegnev;
  }

  public void setSzaMegnev(final String pSzaMegnev) {
    this.szaMegnev = pSzaMegnev;
  }

  public String getSzaSzlatip() {
    return this.szaSzlatip;
  }

  public void setSzaSzlatip(final String pSzaSzlatip) {
    this.szaSzlatip = pSzaSzlatip;
  }

  public String getSzaSzlatipNev() {
    return this.szaSzlatipNev;
  }

  public void setSzaSzlatipNev(final String pSzaSzlatipNev) {
    this.szaSzlatipNev = pSzaSzlatipNev;
  }

  public String getSzaPenzint() {
    return this.szaPenzint;
  }

  public void setSzaPenzint(final String pSzaPenzint) {
    this.szaPenzint = pSzaPenzint;
  }

  public String getSzaPenzintNev() {
    return this.szaPenzintNev;
  }

  public void setSzaPenzintNev(final String pSzaPenzintNev) {
    this.szaPenzintNev = pSzaPenzintNev;
  }

  public String getSzaDeviza() {
    return this.szaDeviza;
  }

  public void setSzaDeviza(final String pSzaDeviza) {
    this.szaDeviza = pSzaDeviza;
  }

  public String getSzaDevizaNev() {
    return this.szaDevizaNev;
  }

  public void setSzaDevizaNev(final String pSzaDevizaNev) {
    this.szaDevizaNev = pSzaDevizaNev;
  }

  public ZonedDateTime getSzaMddat() {
    return this.szaMddat;
  }

  public void setSzaMddat(final ZonedDateTime pSzaMddat) {
    this.szaMddat = pSzaMddat;
  }
}
