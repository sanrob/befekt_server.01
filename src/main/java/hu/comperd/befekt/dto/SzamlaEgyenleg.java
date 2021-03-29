package hu.comperd.befekt.dto;

public class SzamlaEgyenleg {
  /** Számla kódja. */
  private String szeSzaKod;
  /** Számla megnevezése. */
  private String szeSzaMegnev;
  /** Számla típusa. */
  private String szeSzaSzlatip;
  /** Számla típus megnevezése. */
  private String szeSzaSzlatipNev;
  /** Számlát kezelő pénzintézet. */
  private String szeSzaPenzint;
  /** Számlát kezelő pénzintézet megnevezése. */
  private String szeSzaPenzintNev;
  /** Számla devizaneme. */
  private String szeSzaDeviza;
  /** Számla devizanem megnevezése. */
  private String szeSzaDevizaNev;
  /** Számla egyenlege. */
  private double szeEgyenleg;
  /** Árfolyam. */
  private double szeArfolyam;
  /** Árfolyam érték. */
  private double szeArfolyamErtek;

  public SzamlaEgyenleg() {
    //
  }

  public String getSzeSzaKod() {
    return this.szeSzaKod;
  }

  public void setSzeSzaKod(final String pSzeSzaKod) {
    this.szeSzaKod = pSzeSzaKod;
  }

  public String getSzeSzaMegnev() {
    return this.szeSzaMegnev;
  }

  public void setSzeSzaMegnev(final String pSzeSzaMegnev) {
    this.szeSzaMegnev = pSzeSzaMegnev;
  }

  public String getSzeSzaSzlatip() {
    return this.szeSzaSzlatip;
  }

  public void setSzeSzaSzlatip(final String pSzeSzaSzlatip) {
    this.szeSzaSzlatip = pSzeSzaSzlatip;
  }

  public String getSzeSzaSzlatipNev() {
    return this.szeSzaSzlatipNev;
  }

  public void setSzeSzaSzlatipNev(final String pSzeSzaSzlatipNev) {
    this.szeSzaSzlatipNev = pSzeSzaSzlatipNev;
  }

  public String getSzeSzaPenzint() {
    return this.szeSzaPenzint;
  }

  public void setSzeSzaPenzint(final String pSzeSzaPenzint) {
    this.szeSzaPenzint = pSzeSzaPenzint;
  }

  public String getSzeSzaPenzintNev() {
    return this.szeSzaPenzintNev;
  }

  public void setSzeSzaPenzintNev(final String pSzeSzaPenzintNev) {
    this.szeSzaPenzintNev = pSzeSzaPenzintNev;
  }

  public String getSzeSzaDeviza() {
    return this.szeSzaDeviza;
  }

  public void setSzeSzaDeviza(final String pSzeSzaDeviza) {
    this.szeSzaDeviza = pSzeSzaDeviza;
  }

  public String getSzeSzaDevizaNev() {
    return this.szeSzaDevizaNev;
  }

  public void setSzeSzaDevizaNev(final String pSzeSzaDevizaNev) {
    this.szeSzaDevizaNev = pSzeSzaDevizaNev;
  }

  public double getSzeEgyenleg() {
    return this.szeEgyenleg;
  }

  public void setSzeEgyenleg(final double pSzeEgyenleg) {
    this.szeEgyenleg = pSzeEgyenleg;
  }

  public double getSzeArfolyam() {
    return this.szeArfolyam;
  }

  public void setSzeArfolyam(final double pSzeArfolyam) {
    this.szeArfolyam = pSzeArfolyam;
  }

  public double getSzeArfolyamErtek() {
    return this.szeArfolyamErtek;
  }

  public void setSzeArfolyamErtek(final double pSzeArfolyamErtek) {
    this.szeArfolyamErtek = pSzeArfolyamErtek;
  }
}
