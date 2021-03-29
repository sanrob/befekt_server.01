package hu.comperd.befekt.dto;

import java.time.ZonedDateTime;

public class BefektFajta {
  /** Azonosító. */
  private String        id;
  /** Befektetés fajta kódja kódja. */
  private String        bffKod;
  /** Befektetés fajta megnevezése. */
  private String        bffMegnev;
  /** Befektetés fajta típusa: Prompt/Határidő. */
  private String        bffTip;
  /** Befektetés fajta típusának megnevezése: Prompt/Határidő. */
  private String        bffTipNev;
  /** Befektetés fajtához tartozó elszámolási számla. */
  private String        bffSzamla;
  /** Befektetés fajtához tartozó elszámolási számla megnevezése. */
  private String        bffSzamlaNev;
  /** Befektetés jutalékának elszámolási számlája. */
  private String        bffJutSzla;
  /** Befektetés jutalékának elszámolási számlájának megnevezése. */
  private String        bffJutSzlaNev;
  /** Legkisebb köthető egység. */
  private int           bffEgyseg;
  /** Nyitott állapot elszámolás módja. */
  private String        bffNyitElsz;
  /** Nyitott állapot elszámolás módjának megnevezése. */
  private String        bffNyitElszNev;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime bffMddat;

  public BefektFajta() {
    //
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

  public String getBffTipNev() {
    return this.bffTipNev;
  }

  public void setBffTipNev(final String pBffTipNev) {
    this.bffTipNev = pBffTipNev;
  }

  public String getBffSzamla() {
    return this.bffSzamla;
  }

  public void setBffSzamla(final String pBffSzamla) {
    this.bffSzamla = pBffSzamla;
  }

  public String getBffSzamlaNev() {
    return this.bffSzamlaNev;
  }

  public void setBffSzamlaNev(final String pBffSzamlaNev) {
    this.bffSzamlaNev = pBffSzamlaNev;
  }

  public String getBffJutSzla() {
    return this.bffJutSzla;
  }

  public void setBffJutSzla(final String pBffJutSzla) {
    this.bffJutSzla = pBffJutSzla;
  }

  public String getBffJutSzlaNev() {
    return this.bffJutSzlaNev;
  }

  public void setBffJutSzlaNev(final String pBffJutSzlaNev) {
    this.bffJutSzlaNev = pBffJutSzlaNev;
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

  public String getBffNyitElszNev() {
    return this.bffNyitElszNev;
  }

  public void setBffNyitElszNev(final String pBffNyitElszNev) {
    this.bffNyitElszNev = pBffNyitElszNev;
  }

  public ZonedDateTime getBffMddat() {
    return this.bffMddat;
  }

  public void setBffMddat(final ZonedDateTime pBffMddat) {
    this.bffMddat = pBffMddat;
  }
}
