package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bizonylat_sorszamok")
public class BizonylatSorszamCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Bizonylat típus. */
  private String        bizTipus;
  /** Könyvelés éve. */
  private int           bizEv;
  /** Bizonylat sorszáma. */
  private int           bizSorszam;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime bizMddat;

  public BizonylatSorszamCol() {
    //
  }

  public BizonylatSorszamCol(final String pBizTipus, final int pKonyvEv) {
    this.bizTipus = pBizTipus;
    this.bizEv = pKonyvEv;
    this.bizSorszam = 0;
    this.bizMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getBizTipus() {
    return this.bizTipus;
  }

  public void setBizTipus(final String pBizTipus) {
    this.bizTipus = pBizTipus;
  }

  public int getBizEv() {
    return this.bizEv;
  }

  public void setBizEv(final int pBizEv) {
    this.bizEv = pBizEv;
  }

  public int getBizSorszam() {
    return this.bizSorszam;
  }

  public void setBizSorszam(final int pBizSorszam) {
    this.bizSorszam = pBizSorszam;
  }

  public ZonedDateTime getBizMddat() {
    return this.bizMddat;
  }

  public void setBizMddat(final ZonedDateTime pBizMddat) {
    this.bizMddat = pBizMddat;
  }

  @Override
  public String toString() {
    return "BizonylatSorszam["
        + "_id=" + this.id
        + ", bizTipus=" + this.bizTipus
        + ", bizEv=" + this.bizEv
        + ", bizSorszam=" + this.bizSorszam
        + ", bizMddat=" + this.bizMddat
        + "]";
  }
}
