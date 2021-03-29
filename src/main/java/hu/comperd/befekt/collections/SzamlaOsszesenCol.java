package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "szamla_osszesen")
public class SzamlaOsszesenCol {
  /** Azonosító. */
  @Id
  private String        _id;
  /** Számla azonosító. */
  private String        szoSzaAzon;
  /** Forgalom dátuma. */
  private LocalDate     szoForgDat;
  /** Nyitó érték. */
  private double        szoNyito;
  /** Tartozik forgalom. */
  private double        szoTartozik;
  /** Követel forgalom. */
  private double        szoKovetel;
  /** Záró érték. */
  private double        szoZaro;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime szoMddat;

  public SzamlaOsszesenCol() {
    //
  }

  public String get_id() {
    return this._id;
  }

  public void set_id(final String p_id) {
    this._id = p_id;
  }

  public String getSzoSzaAzon() {
    return this.szoSzaAzon;
  }

  public void setSzoSzaAzon(final String pSzoSzaAzon) {
    this.szoSzaAzon = pSzoSzaAzon;
  }

  public LocalDate getSzoForgDat() {
    return this.szoForgDat;
  }

  public void setSzoForgDat(final LocalDate pSzoForgDat) {
    this.szoForgDat = pSzoForgDat;
  }

  public double getSzoNyito() {
    return this.szoNyito;
  }

  public void setSzoNyito(final double pSzoNyito) {
    this.szoNyito = pSzoNyito;
  }

  public double getSzoTartozik() {
    return this.szoTartozik;
  }

  public void setSzoTartozik(final double pSzoTartozik) {
    this.szoTartozik = pSzoTartozik;
  }

  public double getSzoKovetel() {
    return this.szoKovetel;
  }

  public void setSzoKovetel(final double pSzoKovetel) {
    this.szoKovetel = pSzoKovetel;
  }

  public double getSzoZaro() {
    return this.szoZaro;
  }

  public void setSzoZaro(final double pSzoZaro) {
    this.szoZaro = pSzoZaro;
  }

  public ZonedDateTime getSzoMddat() {
    return this.szoMddat;
  }

  public void setSzoMddat(final ZonedDateTime pSzoMddat) {
    this.szoMddat = pSzoMddat;
  }

  @Override
  public String toString() {
    return "SzamlaOsszesen["
        + "id=" + this._id
        + ", szo_sza_azon=" + this.szoSzaAzon
        + ", szo_forg_dat=" + this.szoForgDat
        + ", szo_nyito=" + this.szoNyito
        + ", szo_tartozik=" + this.szoTartozik
        + ", szo_kovetel=" + this.szoKovetel
        + ", szo_zaro=" + this.szoZaro
        + ", szo_mddat=" + this.szoMddat
        + "]";
  }
}
