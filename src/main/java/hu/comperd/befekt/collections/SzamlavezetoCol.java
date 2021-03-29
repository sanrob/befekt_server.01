package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.Szamlavezeto;

@Document(collection = "szamlavezeto")
public class SzamlavezetoCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Számlavezető kódja. */
  private String        szvKod;
  /** Számlavezető megnevezése. */
  private String        szvMegnev;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime szvMddat;

  public SzamlavezetoCol() {
  }

  public SzamlavezetoCol(final Szamlavezeto szamlavezeto) {
    this.id = szamlavezeto.getId();
    this.szvKod = szamlavezeto.getSzvKod();
    this.szvMegnev = szamlavezeto.getSzvMegnev();
    this.szvMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getSzvKod() {
    return this.szvKod;
  }

  public void setSzvKod(final String pSzvKod) {
    this.szvKod = pSzvKod;
  }

  public String getSzvMegnev() {
    return this.szvMegnev;
  }

  public void setSzvMegnev(final String pSzvMegnev) {
    this.szvMegnev = pSzvMegnev;
  }

  public ZonedDateTime getSzvMddat() {
    return this.szvMddat;
  }

  public void setSzvMddat(final ZonedDateTime pSzvMddat) {
    this.szvMddat = pSzvMddat;
  }

  @Override
  public String toString() {
    return "Szamlavezeto["
        + "id=" + this.id
        + ", szv_kod=" + this.szvKod
        + ", szv_megnev=" + this.szvMegnev
        + ", szv_mddat=" + this.szvMddat
        + "]";
  }
}
