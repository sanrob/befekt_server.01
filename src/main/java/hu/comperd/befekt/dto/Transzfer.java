package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import hu.comperd.befekt.collections.TranszferCol;
import hu.comperd.befekt.etc.DomainCsoportok;
import hu.comperd.befekt.repositories.DomainRepository;
import hu.comperd.befekt.repositories.SzamlaRepository;
import hu.comperd.befekt.util.Util;

public class Transzfer {
  /** Azonosító. */
  private String        id;
  /** Transzfer azonosítója. */
  private String        traAzon;
  /** Honnan számla azonosítója. */
  private String        traHonnan;
  /** Honnan számla megnevezése. */
  private String        traHonnanNev;
  /** Hova számla azonosítója. */
  private String        traHova;
  /** Hova számla megnevezése. */
  private String        traHovaNev;
  /** Transzfer leírása. */
  private String        traLeiras;
  /** Transzfer dátuma. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate     traDatum;
  /** Terhelt összeg. */
  private double        traTerheles;
  /** Árfolyam. */
  private double        traArfolyam;
  /** Jóváírt összeg. */
  private double        traJovairas;
  /** Jutalék összege. */
  private double        traJutalek;
  /** Jutalék könyvelése: Honnan számla/Hova számla. */
  private String        traJutKonyv;
  /** Jutalék könyvelése jelző megnevezése. */
  private String        traJutKonyvNev;
  /** Számlaforgalmi könyvelés megtörtént-e? */
  private boolean       traSzamlaKonyv;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime traMddat;

  public Transzfer() {
    //
  }

  public Transzfer(final TranszferCol transzferCol) {
    this.id = transzferCol.getId();
    this.traAzon = Util.isEmpty(transzferCol.getTraAzon()) ? "" : transzferCol.getTraAzon();
    this.traHonnan = transzferCol.getTraHonnan();
    this.traHova = transzferCol.getTraHova();
    this.traLeiras = transzferCol.getTraLeiras();
    this.traDatum = transzferCol.getTraDatum();
    this.traTerheles = transzferCol.getTraTerheles();
    this.traArfolyam = transzferCol.getTraArfolyam();
    this.traJovairas = transzferCol.getTraJovairas();
    this.traJutalek = transzferCol.getTraJutalek();
    this.traJutKonyv = transzferCol.getTraJutKonyv();
    this.traSzamlaKonyv = transzferCol.isTraSzamlaKonyv();
    this.traMddat = transzferCol.getTraMddat();
  }

  public Transzfer setDatas(final SzamlaRepository szaRepo, final DomainRepository domRepo) {
    this.traHonnanNev = szaRepo.findBySzaKod(this.traHonnan).getSzaMegnev();
    this.traHovaNev = szaRepo.findBySzaKod(this.traHova).getSzaMegnev();
    this.traJutKonyvNev = domRepo.findByDomCsoportKodAndDomKod(DomainCsoportok.TRAJUTKON, this.traJutKonyv).getDomMegnev();
    return this;
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getTraAzon() {
    return this.traAzon;
  }

  public void setTraAzon(final String pTraAzon) {
    this.traAzon = pTraAzon;
  }

  public String getTraHonnan() {
    return this.traHonnan;
  }

  public void setTraHonnan(final String pTraHonnan) {
    this.traHonnan = pTraHonnan;
  }

  public String getTraHonnanNev() {
    return this.traHonnanNev;
  }

  public void setTraHonnanNev(final String pTraHonnanNev) {
    this.traHonnanNev = pTraHonnanNev;
  }

  public String getTraHova() {
    return this.traHova;
  }

  public void setTraHova(final String pTraHova) {
    this.traHova = pTraHova;
  }

  public String getTraHovaNev() {
    return this.traHovaNev;
  }

  public void setTraHovaNev(final String pTraHovaNev) {
    this.traHovaNev = pTraHovaNev;
  }

  public String getTraLeiras() {
    return this.traLeiras;
  }

  public void setTraLeiras(final String pTraLeiras) {
    this.traLeiras = pTraLeiras;
  }

  public LocalDate getTraDatum() {
    return this.traDatum;
  }

  public void setTraDatum(final LocalDate pTraDatum) {
    this.traDatum = pTraDatum;
  }

  public double getTraTerheles() {
    return this.traTerheles;
  }

  public void setTraTerheles(final double pTraTerheles) {
    this.traTerheles = pTraTerheles;
  }

  public double getTraArfolyam() {
    return this.traArfolyam;
  }

  public void setTraArfolyam(final double pTraArfolyam) {
    this.traArfolyam = pTraArfolyam;
  }

  public double getTraJovairas() {
    return this.traJovairas;
  }

  public void setTraJovairas(final double pTraJovairas) {
    this.traJovairas = pTraJovairas;
  }

  public double getTraJutalek() {
    return this.traJutalek;
  }

  public void setTraJutalek(final double pTraJutalek) {
    this.traJutalek = pTraJutalek;
  }

  public String getTraJutKonyv() {
    return this.traJutKonyv;
  }

  public void setTraJutKonyv(final String pTraJutKonyv) {
    this.traJutKonyv = pTraJutKonyv;
  }

  public String getTraJutKonyvNev() {
    return this.traJutKonyvNev;
  }

  public void setTraJutKonyvNev(final String pTraJutKonyvNev) {
    this.traJutKonyvNev = pTraJutKonyvNev;
  }

  public boolean isTraSzamlaKonyv() {
    return this.traSzamlaKonyv;
  }

  public void setTraSzamlaKonyv(final boolean pTraSzamlaKonyv) {
    this.traSzamlaKonyv = pTraSzamlaKonyv;
  }

  public ZonedDateTime getTraMddat() {
    return this.traMddat;
  }

  public void setTraMddat(final ZonedDateTime pTraMddat) {
    this.traMddat = pTraMddat;
  }
}
