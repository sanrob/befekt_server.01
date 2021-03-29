package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import hu.comperd.befekt.dto.Transzfer;

@Document(collection = "transzferek")
public class TranszferCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Transzfer azonosítója. */
  private String        traAzon;
  /** Transzfer éve. */
  private int           traEv;
  /** Honnan számla azonosítója. */
  private String        traHonnan;
  /** Hova számla azonosítója. */
  private String        traHova;
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
  /** Jóváírt összeg. */
  private double        traJutalek;
  /** Jutalék könyvelése: Honnan számla/Hova számla. */
  private String        traJutKonyv;
  /** Számlaforgalmi könyvelés megtörtént-e? */
  private boolean       traSzamlaKonyv;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime traMddat;

  public TranszferCol() {
    //
  }

  public TranszferCol(final Transzfer transzfer) {
    this.id = transzfer.getId();
    this.traHonnan = transzfer.getTraHonnan();
    this.traHova = transzfer.getTraHova();
    this.traLeiras = transzfer.getTraLeiras();
    this.traDatum = transzfer.getTraDatum();
    this.traTerheles = transzfer.getTraTerheles();
    this.traArfolyam = transzfer.getTraArfolyam();
    this.traJovairas = transzfer.getTraJovairas();
    this.traJutalek = transzfer.getTraJutalek();
    this.traJutKonyv = transzfer.getTraJutKonyv();
    this.traSzamlaKonyv = transzfer.isTraSzamlaKonyv();
    this.traEv = this.traDatum.getYear();
    this.traMddat = ZonedDateTime.now(ZoneId.systemDefault());
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

  public int getTraEv() {
    return this.traEv;
  }

  public void setTraEv(final int pTraEv) {
    this.traEv = pTraEv;
  }

  public String getTraHonnan() {
    return this.traHonnan;
  }

  public void setTraHonnan(final String pTraHonnan) {
    this.traHonnan = pTraHonnan;
  }

  public String getTraHova() {
    return this.traHova;
  }

  public void setTraHova(final String pTraHova) {
    this.traHova = pTraHova;
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

  @Override
  public String toString() {
    return "Transzfer["
        + "id=" + this.id
        + ", tra_azon=" + this.traAzon
        + ", tra_ev=" + this.traEv
        + ", tra_honnan=" + this.traHonnan
        + ", tra_hova=" + this.traHova
        + ", tra_leiras=" + this.traLeiras
        + ", tra_datum=" + this.traDatum
        + ", tra_terheles=" + this.traTerheles
        + ", tra_arfolyam=" + this.traArfolyam
        + ", tra_jovairas=" + this.traJovairas
        + ", tra_jutalek=" + this.traJutalek
        + ", tra_jut_konyv=" + this.traJutKonyv
        + ", tra_szamla_konyv=" + this.traSzamlaKonyv
        + ", tra_mddat=" + this.traMddat
        + "]";
  }
}
