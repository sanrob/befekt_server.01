package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.KonyvelesiEv;

@Document(collection = "konyvelesi_evek")
public class KonyvelesiEvCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Könyvelés éve. */
  private String        konEv;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime konMddat;

  public KonyvelesiEvCol() {
  }

  public KonyvelesiEvCol(final KonyvelesiEv konyvelesiEv) {
    this.id = konyvelesiEv.getId();
    this.konEv = konyvelesiEv.getKonEv();
    this.konMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public KonyvelesiEvCol(final String pEv) {
    this.konEv = pEv;
    this.konMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getKonEv() {
    return this.konEv;
  }

  public void setKonEv(final String pKonEv) {
    this.konEv = pKonEv;
  }

  public ZonedDateTime getKonMddat() {
    return this.konMddat;
  }

  public void setKonMddat(final ZonedDateTime pKonMddat) {
    this.konMddat = pKonMddat;
  }

  @Override
  public String toString() {
    return "KonyvelesiEv["
        + "_id=" + this.id
        + ", konEv=" + this.konEv
        + ", konMddat=" + this.konMddat
        + "]";
  }
}
