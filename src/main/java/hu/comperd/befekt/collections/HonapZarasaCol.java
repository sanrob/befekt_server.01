package hu.comperd.befekt.collections;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.HonapZarasa;

@Document(collection = "honap_zarasa")
public class HonapZarasaCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Könyvelés időszak - Hónmap. */
  private String        hozHonap;
  /** Lezárás jelző. */
  private boolean       hozZarasJel;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime hozMddat;

  public HonapZarasaCol() {
  }

  public HonapZarasaCol(final HonapZarasa honapZarasa) {
    this.id = honapZarasa.getId();
    this.hozHonap = honapZarasa.getHozHonap();
    this.hozZarasJel = honapZarasa.isHozZarasJel();
    this.hozMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public String getHozHonap() {
    return this.hozHonap;
  }

  public void setHozHonap(final String pHozHonap) {
    this.hozHonap = pHozHonap;
  }

  public boolean isHozZarasJel() {
    return this.hozZarasJel;
  }

  public void setHozZarasJel(final boolean pHozZarasJel) {
    this.hozZarasJel = pHozZarasJel;
  }

  public ZonedDateTime getHozMddat() {
    return this.hozMddat;
  }

  public void setHozMddat(final ZonedDateTime pHozMddat) {
    this.hozMddat = pHozMddat;
  }

  @Override
  public String toString() {
    return "HonapZarasa["
        + "_id=" + this.id
        + ", hoz_honap=" + this.hozHonap
        + ", hoz_Zaras_jel=" + this.isHozZarasJel()
        + ", hoz_mddat=" + this.hozMddat
        + "]";
  }
}
