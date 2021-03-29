package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Szamla;

@Document(collection = "szamlak")
public class SzamlaCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Számla kódja. */
  private String        szaKod;
  /** Számla megnevezése. */
  private String        szaMegnev;
  /** Számla típusa. */
  private String        szaSzlatip;
  /** Számlát kezelő pénzintézet. */
  private String        szaPenzint;
  /** Számla devizaneme. */
  private String        szaDeviza;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime szaMddat;

  public SzamlaCol() {
  }

  public SzamlaCol(final Szamla szamla) {
    this.id = szamla.getId();
    this.szaKod = szamla.getSzaKod();
    this.szaMegnev = szamla.getSzaMegnev();
    this.szaSzlatip = szamla.getSzaSzlatip();
    this.szaPenzint = szamla.getSzaPenzint();
    this.szaDeviza = szamla.getSzaDeviza();
    this.szaMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  public String getSzaPenzint() {
    return this.szaPenzint;
  }

  public void setSzaPenzint(final String pSzaPenzint) {
    this.szaPenzint = pSzaPenzint;
  }

  public String getSzaDeviza() {
    return this.szaDeviza;
  }

  public void setSzaDeviza(final String pSzaDeviza) {
    this.szaDeviza = pSzaDeviza;
  }

  public ZonedDateTime getSzaMddat() {
    return this.szaMddat;
  }

  public void setSzaMddat(final ZonedDateTime pSzaMddat) {
    this.szaMddat = pSzaMddat;
  }

  @Override
  public String toString() {
    return "Szamla["
        + "id=" + this.id
        + ", sza_kod=" + this.szaKod
        + ", sza_megnev=" + this.szaMegnev
        + ", sza_szlatip=" + this.szaSzlatip
        + ", sza_penzint=" + this.szaPenzint
        + ", sza_deviza=" + this.szaDeviza
        + ", sza_mddat=" + this.szaMddat
        + "]";
  }
}
