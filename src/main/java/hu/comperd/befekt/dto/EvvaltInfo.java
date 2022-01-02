package hu.comperd.befekt.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import hu.comperd.befekt.collections.KonyvelesiEvCol;

public class EvvaltInfo {
  /** Könyvelési éev. */
  private String        konyvEv;
  /** Bizonylat sorszámok generálva. */
  private boolean       bizSorszGen;
  /** Könyvelési hónapok generálva. */
  private boolean       konyvHonapGen;
  /** Utolsó módosítás ideje. */
  private LocalDateTime mddat;

  public EvvaltInfo() {
    //
  }

  public EvvaltInfo(final KonyvelesiEvCol pKonyvEv) {
    this.konyvEv = pKonyvEv.getKonEv();
    this.mddat = pKonyvEv.getKonMddat().withZoneSameInstant(ZoneId.of("Europe/Budapest")).toLocalDateTime();
  }

  public String getKonyvEv() {
    return this.konyvEv;
  }

  public void setKonyvEv(final String pKonyvEv) {
    this.konyvEv = pKonyvEv;
  }

  public boolean isBizSorszGen() {
    return this.bizSorszGen;
  }

  public void setBizSorszGen(final boolean pBizSorszGen) {
    this.bizSorszGen = pBizSorszGen;
  }

  public boolean isKonyvHonapGen() {
    return this.konyvHonapGen;
  }

  public void setKonyvHonapGen(final boolean pKonyvHonapGen) {
    this.konyvHonapGen = pKonyvHonapGen;
  }

  public LocalDateTime getMddat() {
    return this.mddat;
  }

  public void setMddat(final LocalDateTime pMddat) {
    this.mddat = pMddat;
  }
}
