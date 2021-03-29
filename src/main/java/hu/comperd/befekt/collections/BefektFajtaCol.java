package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.BefektFajta;

@Document(collection = "befekt_fajtak")
public class BefektFajtaCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Befektetés fajta kódja kódja. */
  private String        bffKod;
  /** Befektetés fajta megnevezése. */
  private String        bffMegnev;
  /** Befektetés fajta típusa: Prompt/Határidő. */
  private String        bffTip;
  /** Befektetés fajtához tartozó elszámolási számla. */
  private String        bffSzamla;
  /** Befektetés jutalékának elszámolási számlája. */
  private String        bffJutSzla;
  /** Legkisebb köthető egység. */
  private int           bffEgyseg;
  /** Nyitott állapot elszámolás módja. */
  private String        bffNyitElsz;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime bffMddat;

  public BefektFajtaCol() {
  }

  public BefektFajtaCol(final BefektFajta befektFajta) {
    this.id = befektFajta.getId();
    this.bffKod = befektFajta.getBffKod();
    this.bffMegnev = befektFajta.getBffMegnev();
    this.bffTip = befektFajta.getBffTip();
    this.bffSzamla = befektFajta.getBffSzamla();
    this.bffJutSzla = befektFajta.getBffJutSzla();
    this.bffEgyseg = befektFajta.getBffEgyseg();
    this.bffNyitElsz = befektFajta.getBffNyitElsz();
    this.bffMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getBffKod() {
    return this.bffKod;
  }

  public void setBffKod(final String pBffKod) {
    this.bffKod = pBffKod;
  }

  public String getBffMegnev() {
    return this.bffMegnev;
  }

  public void setBffMegnev(final String pBffMegnev) {
    this.bffMegnev = pBffMegnev;
  }

  public String getBffTip() {
    return this.bffTip;
  }

  public void setBffTip(final String pBffTip) {
    this.bffTip = pBffTip;
  }

  public String getBffSzamla() {
    return this.bffSzamla;
  }

  public void setBffSzamla(final String pBffSzamla) {
    this.bffSzamla = pBffSzamla;
  }

  public String getBffJutSzla() {
    return this.bffJutSzla;
  }

  public void setBffJutSzla(final String pBffJutSzla) {
    this.bffJutSzla = pBffJutSzla;
  }

  public int getBffEgyseg() {
    return this.bffEgyseg;
  }

  public void setBffEgyseg(final int pBffEgyseg) {
    this.bffEgyseg = pBffEgyseg;
  }

  public String getBffNyitElsz() {
    return this.bffNyitElsz;
  }

  public void setBffNyitElsz(final String pBffNyitElsz) {
    this.bffNyitElsz = pBffNyitElsz;
  }

  public ZonedDateTime getBffMddat() {
    return this.bffMddat;
  }

  public void setBffMddat(final ZonedDateTime pBffMddat) {
    this.bffMddat = pBffMddat;
  }

  @Override
  public String toString() {
    return "BefektFajta["
        + "id=" + this.id
        + ", bff_kod=" + this.bffKod
        + ", bff_megnev=" + this.bffMegnev
        + ", bff_tip=" + this.bffTip
        + ", bff_szamla=" + this.bffSzamla
        + ", bff_jutSzla=" + this.bffJutSzla
        + ", bff_egyseg=" + this.bffEgyseg
        + ", bff_nyitElsz=" + this.bffNyitElsz
        + ", bff_mddat=" + this.bffMddat
        + "]";
  }
}
