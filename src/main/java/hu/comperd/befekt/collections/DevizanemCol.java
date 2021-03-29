package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Devizanem;

@Document(collection = "devizanem")
public class DevizanemCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Devizanem kódja. */
  private String        devKod;
  /** Devizanem megnevezése. */
  private String        devMegnev;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime devMddat;

  public DevizanemCol() {
  }

  public DevizanemCol(final Devizanem devizanem) {
    this.id = devizanem.getId();
    this.devKod = devizanem.getDevKod();
    this.devMegnev = devizanem.getDevMegnev();
    this.devMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getDevKod() {
    return this.devKod;
  }

  public void setDevKod(final String pDevKod) {
    this.devKod = pDevKod;
  }

  public String getDevMegnev() {
    return this.devMegnev;
  }

  public void setDevMegnev(final String pDevMegnev) {
    this.devMegnev = pDevMegnev;
  }

  public ZonedDateTime getDevMddat() {
    return this.devMddat;
  }

  public void setDevMddat(final ZonedDateTime pDevMddat) {
    this.devMddat = pDevMddat;
  }

  @Override
  public String toString() {
    return "Devizanem["
        + "id=" + this.id
        + ", dev_kod=" + this.devKod
        + ", dev_megnev=" + this.devMegnev
        + ", dev_mddat=" + this.devMddat
        + "]";
  }
}
