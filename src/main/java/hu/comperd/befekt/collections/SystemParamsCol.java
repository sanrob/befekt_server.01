package hu.comperd.befekt.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import hu.comperd.befekt.dto.SystemParams;

@Document(collection = "system_params")
public class SystemParamsCol {
  /** Azonosító. */
  @Id
  private String        id;
  /** Rendszer indulásának dátuma. */
  private LocalDate     sysStartDate;
  /** Havi tételes kiadások nyilvántartásának indulásának dátuma. */
  private LocalDate     havKiaStartDate;
  /** Általános kiadás típus kódja. */
  private String        altKiadTipusKod;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime sysMddat;

  public SystemParamsCol() {
  }

  public SystemParamsCol(final SystemParams systemParams) {
    this.id = systemParams.getId();
    this.sysStartDate = systemParams.getSysStartDate();
    this.havKiaStartDate = systemParams.getHavKiaStartDate();
    this.altKiadTipusKod = systemParams.getAltKiadTipusKod();
    this.sysMddat = ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String pId) {
    this.id = pId;
  }

  public LocalDate getSysStartDate() {
    return this.sysStartDate;
  }

  public void setSysStartDate(final LocalDate pSysStartDate) {
    this.sysStartDate = pSysStartDate;
  }

  public LocalDate getHavKiaStartDate() {
    return this.havKiaStartDate;
  }

  public void setHavKiaStartDate(final LocalDate pHavKiaStartDate) {
    this.havKiaStartDate = pHavKiaStartDate;
  }

  public String getAltKiadTipusKod() {
    return this.altKiadTipusKod;
  }

  public void setAltKiadTipusKod(final String pAltKiadTipusKod) {
    this.altKiadTipusKod = pAltKiadTipusKod;
  }

  public ZonedDateTime getSysMddat() {
    return this.sysMddat;
  }

  public void setSysMddat(final ZonedDateTime pSysMddat) {
    this.sysMddat = pSysMddat;
  }

  @Override
  public String toString() {
    return "HonapZarasa["
        + "_id=" + this.id
        + ", sys_start_date=" + this.sysStartDate
        + ", hav_kia_start_date=" + this.havKiaStartDate
        + ", alt_kiad_tipus_kod=" + this.altKiadTipusKod
        + ", sys_mddat=" + this.sysMddat
        + "]";
  }
}
