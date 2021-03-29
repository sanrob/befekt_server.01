package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "szamla_forgalom")
public class SzamlaForgalomCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Számlaforgalmi azonosító. */
  private String        szfAzon;
  /** Számla kódja. */
  private String        szfSzaKod;
  /** Szöveges leírás. */
  private String        szfSzoveg;
  /** Forgalom dátuma. */
  private LocalDate     szfForgDat;
  /** Forgalom típusa. */
  private String        szfTipus;
  /** Hivatkozott bizonylat típusa. */
  private String        szfHivBizTip;
  /** Hivatkozott bizonylat azonosítója. */
  private String        szfHivBizAzon;
  /** Tartozik /Követel jelző. */
  private String        szfTKJel;
  /** Forgalom összege. */
  private double        szfOsszeg;
  /** Párosított összeg. */
  private double        szfParos;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime szfMddat;

  public SzamlaForgalomCol() {
    //
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getSzfAzon() {
    return this.szfAzon;
  }

  public void setSzfAzon(final String pSzfAzon) {
    this.szfAzon = pSzfAzon;
  }

  public String getSzfSzaKod() {
    return this.szfSzaKod;
  }

  public void setSzfSzaKod(final String pSzfSzaKod) {
    this.szfSzaKod = pSzfSzaKod;
  }

  public String getSzfSzoveg() {
    return this.szfSzoveg;
  }

  public void setSzfSzoveg(final String pSzfSzoveg) {
    this.szfSzoveg = pSzfSzoveg;
  }

  public LocalDate getSzfForgDat() {
    return this.szfForgDat;
  }

  public void setSzfForgDat(final LocalDate pSzfForgDat) {
    this.szfForgDat = pSzfForgDat;
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

  public double getSzfParos() {
    return this.szfParos;
  }

  public void setSzfParos(final double pSzfParos) {
    this.szfParos = pSzfParos;
  }

  public ZonedDateTime getSzfMddat() {
    return this.szfMddat;
  }

  public void setSzfMddat(final ZonedDateTime pSzfMddat) {
    this.szfMddat = pSzfMddat;
  }

  @Override
  public String toString() {
    return "SzamlaForgalom["
        + "id=" + this.id
        + ", szf_azon=" + this.szfAzon
        + ", szf_szaKod=" + this.szfSzaKod
        + ", szf_szoveg=" + this.szfSzoveg
        + ", szf_forgDat=" + this.szfForgDat
        + ", szf_tipus=" + this.szfTipus
        + ", szf_hiv_biz_tip=" + this.szfHivBizTip
        + ", szf_hiv_biz_azon=" + this.szfHivBizAzon
        + ", szf_tk_jel=" + this.szfTKJel
        + ", szf_osszeg=" + this.szfOsszeg
        + ", szf_paros=" + this.szfParos
        + ", szf_mddat=" + this.szfMddat
        + "]";
  }
}
