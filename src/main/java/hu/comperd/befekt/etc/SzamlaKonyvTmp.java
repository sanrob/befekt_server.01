package hu.comperd.befekt.etc;

import java.time.LocalDate;

public class SzamlaKonyvTmp {
  /** Könyvelés dátuma. */
  private LocalDate szfForgDat;
  /** Számla azonosító. */
  private String    szfSzaAzon;
  /** Tétel szöveges leírása. */
  private String    szfSzoveg;
  /** Számla típusa. */
  private String    szfTipus;
  /** Hivatkozott bizonylat típusa. */
  private String    szfHivBizTip;
  /** Hivatkozott bizonylat azonosítója. */
  private String    szfHivBizAzon;
  /** Tartozik / Követel jelző. */
  private String    szfTKJel;
  /** Könyvelendő összeg. */
  private double    szfOsszeg;

  public SzamlaKonyvTmp() {
    //
  }

  public LocalDate getSzfForgDat() {
    return this.szfForgDat;
  }

  public void setSzfForgDat(final LocalDate pSzfForgDat) {
    this.szfForgDat = pSzfForgDat;
  }

  public String getSzfSzaAzon() {
    return this.szfSzaAzon;
  }

  public void setSzfSzaAzon(final String pSzfSzaAzon) {
    this.szfSzaAzon = pSzfSzaAzon;
  }

  public String getSzfSzoveg() {
    return this.szfSzoveg;
  }

  public void setSzfSzoveg(final String pSzfSzoveg) {
    this.szfSzoveg = pSzfSzoveg;
  }

  public String getSzfTipus() {
    return this.szfTipus;
  }

  public void setSzfTipus(final String pSzfTipus) {
    this.szfTipus = pSzfTipus;
  }

  public String getSzfHivBizTip() {
    return this.szfHivBizTip;
  }

  public void setSzfHivBizTip(final String pSzfHivBizTip) {
    this.szfHivBizTip = pSzfHivBizTip;
  }

  public String getSzfHivBizAzon() {
    return this.szfHivBizAzon;
  }

  public void setSzfHivBizAzon(final String pSzfHivBizAzon) {
    this.szfHivBizAzon = pSzfHivBizAzon;
  }

  public String getSzfTKJel() {
    return this.szfTKJel;
  }

  public void setSzfTKJel(final String pSzfTKJel) {
    this.szfTKJel = pSzfTKJel;
  }

  public double getSzfOsszeg() {
    return this.szfOsszeg;
  }

  public void setSzfOsszeg(final double pSzfOsszeg) {
    this.szfOsszeg = pSzfOsszeg;
  }
}
