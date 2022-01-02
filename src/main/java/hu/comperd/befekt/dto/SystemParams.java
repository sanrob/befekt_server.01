package hu.comperd.befekt.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import hu.comperd.befekt.collections.SystemParamsCol;

public class SystemParams {
  /** Azonosító. */
  private String        id;
  /** Rendszer indulásának dátuma. */
  private LocalDate     sysStartDate;
  /** Havi tételes kiadások nyilvántartásának indulásának dátuma. */
  private LocalDate     havKiaStartDate;
  /** Általános kiadás típus kódja. */
  private String        altKiadTipusKod;
  /** Utolsó módosítás ideje. */
  private ZonedDateTime sysMddat;

  public SystemParams() {
    //
  }

  public SystemParams(final SystemParamsCol systemParamsCol) {
    this.id = systemParamsCol.getId();
    this.sysStartDate = systemParamsCol.getSysStartDate();
    this.havKiaStartDate = systemParamsCol.getHavKiaStartDate();
    this.altKiadTipusKod = systemParamsCol.getAltKiadTipusKod();
    this.sysMddat = systemParamsCol.getSysMddat();
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
}
